import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Test;

import entities.Car;
 


public class Runner {
	@Test
    public void crud() {
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
				.configure() // configures settings from hibernate.cfg.xml
				.build();
		
		SessionFactory sessionFactory = new MetadataSources( registry )
				.buildMetadata().buildSessionFactory();
        
        Session session = sessionFactory.openSession();
         
        create(session);
        read(session);
         
        update(session);
        read(session);
         
        delete(session);
        read(session);
         
        session.close();
    }
	
	private void create(Session session) {
        System.out.println("Creating car records...");
        Car mustang = new Car();
        mustang.setModel("mustang");
        mustang.setPrice("£40,000.00");
         
        Car mondeo = new Car();
        mondeo.setModel("mondeo");
        mondeo.setPrice("£20,000.00");
         
        session.beginTransaction();
        session.save(mustang);
        session.save(mondeo);
        session.getTransaction().commit();
    }
	
	private void read(Session session) {
		session.beginTransaction();
		List<Car> result = (List<Car>)session.createQuery( "from Car" ).list();
		System.out.println("Reading car records...");
		System.out.printf("%-30.30s  %-30.30s%n", "Model", "Price");
		for (Car c :  result ) {
			System.out.printf("%-30.30s  %-30.30s%n", c.getModel(), c.getPrice());
		}
		session.getTransaction().commit();
		//session.close();
    }

	private void delete(Session session) {
        System.out.println("Deleting mondeo record...");
        Car mondeo = (Car) session.get(Car.class, "mondeo");
         
        session.beginTransaction();
        session.delete(mondeo);
        session.getTransaction().commit();
    }
     
    private void update(Session session) {
        System.out.println("Updating mustang price...");
        Car mustang = (Car) session.get(Car.class, "mustang");
        //mustang.setModel("mustang232");
        mustang.setPrice("£35,250.00");
         
        session.beginTransaction();
        session.update(mustang);
        session.getTransaction().commit();
    }
}
