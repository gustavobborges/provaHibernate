/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.Profissao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author gustavo
 */
public interface ProfissaoDao extends BaseDao<Profissao, Long>{
    List<Profissao> pesquisarPorNome(String nome, Session session) throws HibernateException;
}
