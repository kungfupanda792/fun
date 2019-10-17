package com.inter;

import org.springframework.data.jpa.repository.JpaRepository;
import com.f.Designation;

public interface record2 extends JpaRepository<Designation, Integer> {

    Designation findByJobTitle(String str);
Designation findFirstByJobTitle(String id);
Designation findByJid(Integer id);
}
