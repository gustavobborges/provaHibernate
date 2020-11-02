/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.Profissao;
import br.com.avaliacaohibernate.util.UtilTeste;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gustavo
 */
public class ProfissaoDaoImplTest {
    
    private Profissao profissao;
    private ProfissaoDao profissaoDao;
    private Session session;
    
    public ProfissaoDaoImplTest() {
        profissaoDao = new ProfissaoDaoImpl();
    }
      
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        profissao = new Profissao(null,
                UtilTeste.gerarProfissao(),
                UtilTeste.gerarDescProfissao()
        );   
              
        session = HibernateUtil.abrirSessao();
        profissaoDao.salvarOuAlterar(profissao, session);
        session.close();
        assertNotNull(profissao.getId());
    }
    
   @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarProfissaoBD();
        profissao.setNome("NovaProf. " + UtilTeste.gerarProfissao());
        profissao.setDescricao("NovaDesc." + UtilTeste.gerarDescProfissao());

        session = HibernateUtil.abrirSessao();
        profissaoDao.salvarOuAlterar(profissao, session);       
        Profissao profissaoAlterada = profissaoDao.pesquisarPorId(profissao.getId(), session);    
        session.close();
        assertEquals(profissao.getNome(), profissaoAlterada.getNome());    
     }
    
    @Test
    public void testExcluir() {
        System.out.println("Exluir");
        buscarProfissaoBD();
        session = HibernateUtil.abrirSessao();
        profissaoDao.remover(profissao, session);
        Profissao profissaoExcluida = profissaoDao.pesquisarPorId(profissao.getId(), session);
        session.close();  
        assertNull(profissaoExcluida);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarProfissaoBD();
        session = HibernateUtil.abrirSessao();
        List<Profissao> profissoes = profissaoDao.pesquisarPorNome(profissao.getNome(), session);
        assertTrue(profissoes.size() > 0);
    }
    
    public Profissao buscarProfissaoBD() {
    session = HibernateUtil.abrirSessao();
    Query consulta = session.createQuery("FROM Profissao");
    List<Profissao> profissoes = consulta.list();
    if(profissoes.isEmpty()) {
        session.close();
        testSalvar();
    } else {
        profissao = profissoes.get(0);
        session.close();
    }
    return profissao;
    }
}