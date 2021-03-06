package Server.Database;

import Server.Entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure().addAnnotatedClass(UsersEntity.class).addAnnotatedClass(BookEntity.class)
                        .addAnnotatedClass(BasketEntity.class).addAnnotatedClass(OrdersEntity.class).addAnnotatedClass(AdminEntity.class)
                        .addAnnotatedClass(DiscountEntity.class).addAnnotatedClass(NotificationEntity.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}