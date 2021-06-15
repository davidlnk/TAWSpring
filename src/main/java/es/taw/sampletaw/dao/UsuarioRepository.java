package es.taw.sampletaw.dao;

import es.taw.sampletaw.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT a FROM Usuario a WHERE a.nickname = :nick AND a.contrasena = :password")
    public Usuario findByNickAndPassword(String nick, String password);

    @Query("SELECT a FROM Usuario a WHERE a.nickname = :nick")
    public Usuario findByNick(String nick);

    @Query("SELECT a FROM Usuario a WHERE a.nickname = :nick")
    public Usuario esNickUnico(String nick);

    @Query("SELECT c FROM Usuario c WHERE UPPER(c.nickname) LIKE UPPER(CONCAT('%', :filtro,'%'))")
    public List<Usuario> findBySimilarNick(String filtro);

    @Query("SELECT c FROM Usuario c WHERE UPPER(c.contrasena) LIKE UPPER(CONCAT('%', :filtro,'%'))")
    public List<Usuario> findBySimilarContrasena(String filtro);

    @Query("SELECT c FROM Usuario c WHERE UPPER(c.tipoUsuario) LIKE UPPER(CONCAT('%', :filtro,'%'))")
    public List<Usuario> findBySimilarTipoUsuario(String filtro);
}
