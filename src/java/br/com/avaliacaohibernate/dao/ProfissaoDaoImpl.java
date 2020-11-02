/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.Profissao;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gustavo
 */
public class ProfissaoDaoImpl extends BaseDaoImpl<Profissao, Long> implements ProfissaoDao, Serializable {

    @Override
    public Profissao pesquisarPorId(Long id, Session session) throws HibernateException {
        return (Profissao) session.get(Profissao.class, id);
    }

    @Override
    public List<Profissao> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Profissao WHERE nome LIKE :nomeProfissao");
        consulta.setParameter("nomeProfissao", "%" + nome + "%");
        return consulta.list();
    }  
}