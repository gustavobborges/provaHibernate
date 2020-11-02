/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.PessoaFisica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gustavo
 */
public class PessoaFisicaDaoImpl extends BaseDaoImpl<PessoaFisica, Long> implements PessoaFisicaDao, Serializable {

    @Override
    public PessoaFisica pesquisarPorId(Long id, Session session) throws HibernateException {
        return (PessoaFisica) session.get(PessoaFisica.class, id);
    }

    @Override
    public List<PessoaFisica> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("SELECT DISTINCT(p) from PessoaFisica p JOIN FETCH p.cartoes WHERE nome LIKE :nomePessoaFisica");
        consulta.setParameter("nomePessoaFisica", "%" + nome + "%");
        return consulta.list();
    }  
}