package com.example.demo.charts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChartsDao extends JpaRepository<Charts, Integer > {
}
