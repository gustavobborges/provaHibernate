/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.avaliacaohibernate.dao;

import br.com.avaliacaohibernate.entidade.Cartao;
import br.com.avaliacaohibernate.entidade.PessoaFisica;
import static br.com.avaliacaohibernate.entidade.PessoaFisica_.profissao;
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
public class PessoaFisicaDaoImplTest {
    
    private PessoaFisica pessoaFisica;
    private PessoaFisicaDao pessoaFisicaDao;
    private Session session;
    private Profissao profissao;
    
    public PessoaFisicaDaoImplTest() {
        pessoaFisicaDao = new PessoaFisicaDaoImpl();
    }
    
    @Test
    public void testSalvar() {
        System.out.println("salvar");
        pessoaFisica = new PessoaFisica(null,
                UtilTeste.gerarNome(),
                UtilTeste.gerarEmail(),
                UtilTeste.gerarCpf(),
                UtilTeste.gerarRg()
        );   
        Profissao profissao = new Profissao(null,
                UtilTeste.gerarProfissao(),
                UtilTeste.gerarDescProfissao()
        );
        pessoaFisica.setProfissao(profissao);
//        profissao.setProfissao(pessoaFisica);

        List<Cartao> cartoes = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            cartoes.add(criarCartao());
        }
        
        pessoaFisica.setCartoes(cartoes);
        for (Cartao cartao : cartoes) {
            cartao.setCliente(pessoaFisica);
        }
        
        session = HibernateUtil.abrirSessao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, session);
        session.close();
        assertNotNull(pessoaFisica.getId());
        assertNotNull(pessoaFisica.getCartoes().get(0).getId());
        assertNotNull(pessoaFisica.getProfissao().getId());
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
        buscarPessoaFisicaBD();
        pessoaFisica.setNome("NomeAlt " + UtilTeste.gerarNome());
        pessoaFisica.setEmail("alt" + UtilTeste.gerarEmail());
        pessoaFisica.setCpf(UtilTeste.gerarCpf());
        pessoaFisica.setRg(UtilTeste.gerarRg());
        pessoaFisica.getCartoes().get(0).setNumero(UtilTeste.gerarNumeroCartao());
        pessoaFisica.getProfissao().setNome("Nova Prof. " + UtilTeste.gerarProfissao());

        session = HibernateUtil.abrirSessao();
        pessoaFisicaDao.salvarOuAlterar(pessoaFisica, session);
        
        PessoaFisica pessoaFisicaAlterada = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), session);
        Cartao cartao = pessoaFisicaAlterada.getCartoes().get(0);
        Profissao profissao = pessoaFisicaAlterada.getProfissao();
        session.close();
        
        assertEquals(pessoaFisica.getNome(), pessoaFisicaAlterada.getNome());
        assertEquals(pessoaFisica.getCartoes().get(0).getNumero(), cartao.getNumero());
        assertEquals(pessoaFisica.getProfissao().getNome(), profissao.getNome());
     }
    
    @Test
    public void testExcluir() {
        System.out.println("Exluir");
        buscarPessoaFisicaBD();
        session = HibernateUtil.abrirSessao();
        pessoaFisicaDao.remover(pessoaFisica, session);
        PessoaFisica pessoaFisicaExcluido = pessoaFisicaDao.pesquisarPorId(pessoaFisica.getId(), session);
        session.close();  
        assertNull(pessoaFisicaExcluido);
    }

    @Test
    public void testPesquisarPorNome() {
        System.out.println("pesquisarPorNome");
        buscarPessoaFisicaBD();
        session = HibernateUtil.abrirSessao();
        List<PessoaFisica> pessoasFisicas = pessoaFisicaDao.pesquisarPorNome(pessoaFisica.getNome(), session);
        assertTrue(pessoasFisicas.size() > 0);
    }
    
    public PessoaFisica buscarPessoaFisicaBD() {
    session = HibernateUtil.abrirSessao();
    Query consulta = session.createQuery("SELECT DISTINCT(p) FROM PessoaFisica p JOIN FETCH p.cartoes");
    List<PessoaFisica> pessoasJuridicas = consulta.list();
    if(pessoasJuridicas.isEmpty()) {
        session.close();
        testSalvar();
    } else {
        pessoaFisica = pessoasJuridicas.get(0);
        session.close();
    }
    return pessoaFisica;
    }
}
