package Server.Model;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class AdminEntity {
    @Id
    @Column(name = "id_admin")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAdmin;
    @ManyToOne(fetch= FetchType.LAZY,
            cascade= CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private UsersEntity user;

    public AdminEntity() {}

    public UsersEntity getUser() {
        return user;
    }

    public void setUser(UsersEntity user) {
        this.user = user;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminEntity that = (AdminEntity) o;
        return idAdmin == that.idAdmin;
    }
}
