package isys3461.lab_test_2.beta_service.beta.repo;

import org.springframework.data.jpa.domain.Specification;

import isys3461.lab_test_2.beta_service.beta.model.BetaModel;

public class BetaSpecification {
  static public Specification<BetaModel> hasSomething(String something) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("something"), something);
    };
  }

  static public Specification<BetaModel> orderBySomething(Specification<BetaModel> spec) {
    return (root, query, criteriaBuilder) -> {
      query.orderBy(criteriaBuilder.desc(root.get("something")));
      return spec.toPredicate(root, query, criteriaBuilder);
    };
  }

}
