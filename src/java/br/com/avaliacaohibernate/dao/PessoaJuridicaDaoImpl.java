/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.PessoaJuridica;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author gustavo
 */
public class PessoaJuridicaDaoImpl extends BaseDaoImpl<PessoaJuridica, Long> implements PessoaJuridicaDao, Serializable {

    @Override
    public PessoaJuridica pesquisarPorId(Long id, Session session) throws HibernateException {
        return (PessoaJuridica) session.get(PessoaJuridica.class, id);
    }

    @Override
    public List<PessoaJuridica> pesquisarPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("SELECT DISTINCT(p) from PessoaJuridica p JOIN FETCH p.cartoes WHERE nome LIKE :nomePessoaJuridica");
        consulta.setParameter("nomePessoaJuridica", "%" + nome + "%");
        return consulta.list();
    }  
}