package isys3461.lab_test2.alpha_service.alpha.repo;

import org.springframework.data.jpa.domain.Specification;

import isys3461.lab_test2.alpha_service.alpha.model.AlphaModel;

public class AlphaSpecification {
  static public Specification<AlphaModel> hasSomething(String something) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get("something"), something);
    };
  }

  static public Specification<AlphaModel> orderBySomething(Specification<AlphaModel> spec) {
    return (root, query, criteriaBuilder) -> {
      query.orderBy(criteriaBuilder.desc(root.get("something")));
      return spec.toPredicate(root, query, criteriaBuilder);
    };
  }

}
