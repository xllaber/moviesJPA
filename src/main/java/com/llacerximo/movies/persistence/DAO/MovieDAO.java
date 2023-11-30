package com.llacerximo.movies.persistence.DAO;

import com.llacerximo.movies.db.DBUtil;
import com.llacerximo.movies.exceptions.DBConnectionException;
import com.llacerximo.movies.exceptions.SQLStatmentException;
import com.llacerximo.movies.mapper.MovieMapper;
import com.llacerximo.movies.persistence.model.ActorEntity;
import com.llacerximo.movies.persistence.model.MovieCharacterEntity;
import com.llacerximo.movies.persistence.model.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public interface MovieDAO extends JpaRepository<MovieEntity, Integer> {

    List<MovieEntity> findByTitle(String title);

}
