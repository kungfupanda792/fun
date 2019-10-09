package com.head.h1.inter;

import org.springframework.data.jpa.repository.JpaRepository;import com.head.h1.fun2.detail2;

public interface record2 extends JpaRepository<detail2, Integer> {
detail2 findBydesi(String id);
detail2 findFirstByDesi(String id);
}
