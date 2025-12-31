package isys3461.lab_test2.alpha_service.alpha.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import isys3461.lab_test2.alpha_api.external.dto.alpha.TestKafkaNotifyDto.TestKafkaNotifyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.ListBetaByIdsDto.ListBetaByIdsReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyReq;
import isys3461.lab_test2.alpha_api.external.dto.beta.TestKafkaReqResDto.TestKafkaRequestReplyRes;
import isys3461.lab_test2.alpha_api.external.service.EventProducer;
import isys3461.lab_test2.alpha_api.internal.dto.CreateAlphaDto.CreateAlphaReq;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaDto.GetAlphaRes;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaWithBetaDto.GetAlphaWithBetaRes;
import isys3461.lab_test2.alpha_api.internal.dto.GetAlphaWithBetaDto.GetAlphaWithBetaResBeta;
import isys3461.lab_test2.alpha_api.internal.dto.ListAlphasDto.ListAlphasReq;
import isys3461.lab_test2.alpha_api.internal.dto.ListAlphasDto.ListAlphasRes;
import isys3461.lab_test2.alpha_api.internal.dto.UpdateAlphaDto.UpdateAlphaReq;
import isys3461.lab_test2.alpha_api.internal.service.AlphaInternalService;
import isys3461.lab_test2.alpha_service.alpha.model.AlphaModel;
import isys3461.lab_test2.alpha_service.alpha.repo.AlphaRepo;
import isys3461.lab_test2.alpha_service.alpha.repo.AlphaSpecification;
import isys3461.lab_test2.alpha_service.common.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("null")
public class AlphaInternalServiceImpl implements AlphaInternalService {
  @Autowired
  private AlphaRepo alphaRepo;

  @Autowired
  private EventProducer eventProducer;

  @Autowired
  private RedisTemplate<String, Object> redisTemplate;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void testKafkaNotify(TestKafkaNotifyReq req) {
    eventProducer.testKafkaNotify(req);
  }

  @Override
  public TestKafkaRequestReplyRes testKafkaRequestReply(TestKafkaRequestReplyReq req) {
    var res = eventProducer.testKafkaRequestReply(req);
    return res;
  }

  @Override
  public Page<ListAlphasRes> listAlphas(ListAlphasReq req) {
    var pageable = PageRequest.of(req.pageNo() - 1, req.pageSz());
    var alphas = alphaRepo.findAll(pageable);

    var res = alphas.map(al -> {
      return new ListAlphasRes(al.getId(), al.getName(), al.getPrice());
    });
    return res;
  }

  @Override
  public GetAlphaRes getAlpha(UUID id) {
    var redisKey = RedisUtil.buildGetAlphaKey(id);
    try {
      var cached = redisTemplate.opsForValue().get(redisKey);
      if (cached != null) {
        log.info("found redis key:" + redisKey);
        return (GetAlphaRes) cached;
      }
    } catch (Exception e) {
      log.warn("Failed to read from cache: " + e.getMessage());
    }

    var alpha = alphaRepo.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "alpha not found with id " + id.toString()));
    var res = new GetAlphaRes(id, alpha.getName(), alpha.getPrice(), alpha.getCreatedAt());
    redisTemplate.opsForValue().set(redisKey, res, Duration.ofMinutes(10));
    return res;
  }

  @Override
  public GetAlphaWithBetaRes getAlphaWithBeta(UUID id) {
    var alpha = alphaRepo.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "alpha not found with id " + id.toString()));

    var betaIds = Arrays.asList(UUID.randomUUID());
    var betas = eventProducer.listBetaByIds(new ListBetaByIdsReq(betaIds));
    return new GetAlphaWithBetaRes(
        id,
        alpha.getName(),
        alpha.getPrice(),
        betas.items().stream().map(be -> {
          return new GetAlphaWithBetaResBeta(be.id(), be.name());
        }).toList());
  }

  @Override
  public UUID createAlpha(CreateAlphaReq req) {
    var cond = AlphaSpecification.hasName(req.name());
    var alpha_ = alphaRepo.findOne(cond);
    if (alpha_.isPresent()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "alpha already exist with name " + req.name());
    }

    var newAlpha = alphaRepo.save(new AlphaModel(
        UUID.randomUUID(), req.name(), req.price(), LocalDateTime.now()));
    return newAlpha.getId();
  }

  @Override
  public void updateAlpha(UUID id, UpdateAlphaReq req) {
    var alpha = alphaRepo.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "alpha not found with id " + id.toString()));

    if (req.name() != null) {
      alpha.setName(req.name());
    }

    if (req.price() != null) {
      alpha.setPrice(req.price());
    }

    var newAlpha = alphaRepo.save(alpha);
    var redisKey = RedisUtil.buildGetAlphaKey(id);
    redisTemplate.delete(redisKey);
  }

  @Override
  public void deleteAlpha(UUID id) {
    var alpha = alphaRepo.findById(id)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "alpha not found with id " + id.toString()));

    alphaRepo.deleteById(id);
    var redisKey = RedisUtil.buildGetAlphaKey(id);
    redisTemplate.delete(redisKey);
  }
}
