/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.Cartao;
import br.com.avaliacaohibernate.entidade.PessoaJuridica;
import br.com.avaliacaohibernate.util.UtilTeste;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author gustavo
 */
public class PessoaJuridicaDaoImplTest {
    
    private PessoaJuridica pessoaJuridica;
    private PessoaJuridicaDao pessoaJuridicaDao;
    private Session session;
    
    public PessoaJuridicaDaoImplTest() {
        pessoaJuridicaDao = new PessoaJuridicaDaoImpl();
    }
    
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        pessoaJuridica = new PessoaJuridica(null,
                UtilTeste.gerarNome(),
                UtilTeste.gerarEmail(),
                UtilTeste.gerarCnpj(),
                UtilTeste.gerarInscricao()
        );              

        List<Cartao> cartoes = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            cartoes.add(criarCartao());
        }
        
        pessoaJuridica.setCartoes(cartoes);
        for (Cartao cartao : cartoes) {
            cartao.setCliente(pessoaJuridica);
        }
        
        session = HibernateUtil.abrirSessao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, session);
        session.close();
        assertNotNull(pessoaJuridica.getId());
        assertNotNull(pessoaJuridica.getCartoes().get(0).getId());
    }
    
    private Cartao criarCartao() {
        Cartao cartao = new Cartao(null,
                UtilTeste.gerarNumeroCartao(),
                "Visa",
                "01/2021"
        );
    return cartao;
    }

   @Test
    public void testAlterar() {
        System.out.println("alterar");
        buscarPessoaJuridicaBD();
        pessoaJuridica.setNome("NomeAlt " + UtilTeste.gerarNome());
        pessoaJuridica.setEmail("alt" + UtilTeste.gerarEmail());
        pessoaJuridica.setCnpj(UtilTeste.gerarCnpj());
        pessoaJuridica.setInscricaoEstadual(UtilTeste.gerarInscricao());
        pessoaJuridica.getCartoes().get(0).setNumero(UtilTeste.gerarNumeroCartao());
        
        session = HibernateUtil.abrirSessao();
        pessoaJuridicaDao.salvarOuAlterar(pessoaJuridica, session);
        
        PessoaJuridica pessoaJuridicaAlterado = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), session);
        Cartao cartao = pessoaJuridicaAlterado.getCartoes().get(0);
        session.close();
        
        assertEquals(pessoaJuridica.getNome(), pessoaJuridicaAlterado.getNome());
        assertEquals(pessoaJuridica.getCartoes().get(0).getNumero(), cartao.getNumero());
     }
    
    @Test
    public void testExcluir() {
        System.out.println("Exluir");
        buscarPessoaJuridicaBD();
        session = HibernateUtil.abrirSessao();
        pessoaJuridicaDao.remover(pessoaJuridica, session);
        PessoaJuridica pessoaJuridicaExcluido = pessoaJuridicaDao.pesquisarPorId(pessoaJuridica.getId(), session);
        session.close();  
        assertNull(pessoaJuridicaExcluido);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarPessoaJuridicaBD();
        session = HibernateUtil.abrirSessao();
        List<PessoaJuridica> pessoasJuridicas = pessoaJuridicaDao.pesquisarPorNome(pessoaJuridica.getNome(), session);
        assertTrue(pessoasJuridicas.size() > 0);
    }
    
    public PessoaJuridica buscarPessoaJuridicaBD() {
    session = HibernateUtil.abrirSessao();
    Query consulta = session.createQuery("SELECT DISTINCT(p) FROM PessoaJuridica p JOIN FETCH p.cartoes");
    List<PessoaJuridica> pessoasJuridicas = consulta.list();
    if(pessoasJuridicas.isEmpty()) {
        session.close();
        testSalvar();
    } else {
        pessoaJuridica = pessoasJuridicas.get(0);
        session.close();
    }
    return pessoaJuridica;
    }
}
