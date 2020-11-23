package Server.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class UsersEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_user;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<BasketEntity> basketList;
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "user",
            cascade = CascadeType.ALL)
    private List<AdminEntity> adminEntities;

    public UsersEntity(){}
    public UsersEntity(int idUser, String login, String password){
        this.id_user=idUser;
        this.password=password;
        this.login=login;
    }

    public int getIdUser() {
        return id_user;
    }

    public void setIdUser(int idUser) {
        this.id_user = idUser;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String username) {
        this.login = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<BasketEntity> getBasketList() {
        return basketList;
    }
    public void setBasketList(List<BasketEntity> basketList) {
        this.basketList = basketList;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UsersEntity that = (UsersEntity) o;

        if (id_user != that.id_user) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }
}
