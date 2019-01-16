/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package receipt;

import core.Config;
import core.ReceiptStatus;
import entities.Payment;
import entities.Receipt;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TemporalType;

public class ReceiptService {

    public Receipt create(Receipt receipt) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(receipt);
        em.getTransaction().commit();

        em.close();
        factory.close();
        return receipt;
    }

    public List<Receipt> all() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        List<Receipt> res = em.createNamedQuery("Receipt.FindAll", Receipt.class).getResultList();
        em.getTransaction().commit();

        em.close();
        factory.close();
        return res;
    }

    List<Receipt> search(String param) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        List<Receipt> res = new ArrayList<>();

        em.getTransaction().begin();
        try {
            int id = Integer.parseInt(param);
            Receipt singleResult = em.createNamedQuery("Receipt.byid", Receipt.class).setParameter("id", id)
                    .getSingleResult();
            res.add(singleResult);
        } catch (NumberFormatException ex) {
            res = em.createNamedQuery("Receipt.search", Receipt.class)
                    .setParameter("param", "%" + param + "%")
                    .getResultList();
        }
        em.getTransaction().commit();
        em.close();
        factory.close();
        return res;
    }

    Payment createPayment(Receipt r, Payment item) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        em.persist(item);
        double paid = 0;
        for (Payment p : r.getPayments()) {
            paid += p.getAmount().doubleValue();
        }
        if (paid >= r.getAmount()) {
            r.setStatus(ReceiptStatus.PAID);
        } else if (paid > 0 && paid < r.getAmount()) {
            r.setStatus(ReceiptStatus.PARTIAL_PAID);
        } else {
            r.setStatus(ReceiptStatus.PENDING);
        }
        em.merge(r);
        em.getTransaction().commit();

        em.close();
        factory.close();
        return item;
    }

    Receipt delete(Receipt receipt) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        Receipt merged = em.merge(receipt);
        em.remove(merged);
        em.getTransaction().commit();

        em.close();
        factory.close();
        return receipt;
    }

    public Receipt update(Receipt item) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        int val = em.createNamedQuery("ReceiptItem.Delete")
                .setParameter("id", item.getId())
                .executeUpdate();

//        System.out.println(val);
        Receipt merged = em.merge(item);

        em.getTransaction().commit();

        em.close();
        factory.close();
        return merged;
    }

    Receipt updatePayment(Receipt item) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();

        int val = em.createNamedQuery("Payment.delete")
                .setParameter("param", item.getId())
                .executeUpdate();

//        System.out.println(val);
        Receipt merged = em.merge(item);

        em.getTransaction().commit();

        em.close();
        factory.close();
        return merged;
    }

    List<Receipt> latest() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        List<Receipt> res = em.createNamedQuery("Receipt.latest", Receipt.class).getResultList();
        em.getTransaction().commit();

        em.close();
        factory.close();
        return res;
    }

    List<Receipt> oldest() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        List<Receipt> res = em.createNamedQuery("Receipt.oldest", Receipt.class).getResultList();
        em.getTransaction().commit();

        em.close();
        factory.close();
        return res;
    }

    List<Receipt> filter(Date from, Date to) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(Config.PU);
        EntityManager em = factory.createEntityManager();

        em.getTransaction().begin();
        List<Receipt> res = em.createNamedQuery("Receipt.dates", Receipt.class)
                .setParameter("from", from,TemporalType.DATE)
                .setParameter("to", to,TemporalType.DATE)
                .getResultList();
        em.getTransaction().commit();

        em.close();
        factory.close();
        return res;
    }

}
