/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;


import br.com.avaliacaohibernate.entidade.Cartao;
import br.com.avaliacaohibernate.entidade.Cliente;
import br.com.avaliacaohibernate.entidade.PessoaFisica;
import br.com.avaliacaohibernate.entidade.PessoaJuridica;
import br.com.avaliacaohibernate.entidade.Profissao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get S ession Factory
 * object.
 *
 * @author gusta
 */
public class HibernateUtil {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Cliente.class);
            cfg.addAnnotatedClass(PessoaFisica.class);
            cfg.addAnnotatedClass(PessoaJuridica.class);
            cfg.addAnnotatedClass(Profissao.class);
            cfg.addAnnotatedClass(Cartao.class);

            cfg.configure("/br/com/avaliacaohibernate/dao/hibernate.cfg.xml");
            ServiceRegistry servico = new StandardServiceRegistryBuilder().
                    applySettings(cfg.getProperties()).build();
            
            sessionFactory = cfg.buildSessionFactory(servico);
            
            
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static Session abrirSessao() {
        return sessionFactory.openSession();
    }
}
