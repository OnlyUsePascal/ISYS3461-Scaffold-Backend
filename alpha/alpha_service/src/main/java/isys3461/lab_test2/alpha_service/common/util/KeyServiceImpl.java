package isys3461.lab_test2.alpha_service.common.util;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KeyServiceImpl implements KeyService {
  private KeyStore keyStore;
  private String storePass;

  @Value("${key-store.alias.jws}")
  private String keyAliasJws;

  @Autowired
  public KeyServiceImpl(
      @Value("${key-store.type}") String storeType,
      @Value("${key-store.path}") Resource storeResource,
      @Value("${key-store.password}") String storePass) throws Exception {
    this.storePass = storePass;
    keyStore = KeyStore.getInstance(storeType);
    keyStore.load(storeResource.getInputStream(), storePass.toCharArray());
  }

  @Override
  public PublicKey getJwsPublickey() throws Exception {
    return keyStore.getCertificate(keyAliasJws).getPublicKey();
  }
}
