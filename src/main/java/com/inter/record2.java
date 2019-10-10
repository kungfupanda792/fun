package com.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import com.f.detail2;

public interface record2 extends JpaRepository<detail2, Integer> {
detail2 findBydesi(String id);
detail2 findFirstByDesi(String id);
}
