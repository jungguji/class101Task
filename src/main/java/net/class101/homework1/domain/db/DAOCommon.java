package net.class101.homework1.domain.db;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DAOCommon<T, ID> implements DAO<T,ID> {
    private final Class<T> type;

    public <S extends T> S save(S entity) {
        EntityManager em =  EntityManagerHelper.getEntityManager();
        try {
            EntityManagerHelper.beginTransaction();
            em.persist(entity);
            EntityManagerHelper.commit();
        } catch (Exception e) {
            e.printStackTrace();
            EntityManagerHelper.rollback();
        }

        return entity;
    }

    public Optional<T> findById(ID o) {
        return Optional.of(EntityManagerHelper.getEntityManager().find(this.type, o));
    }

    public List<T> findAll() {
        EntityManager em =  EntityManagerHelper.getEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(this.type);
        criteria.from(type);

        return em.createQuery(criteria).getResultList();
    }

    public void delete(T entity) {
        EntityManager em =  EntityManagerHelper.getEntityManager();

        try {
            EntityManagerHelper.beginTransaction();
            em.remove(entity);
            EntityManagerHelper.commit();
        } catch (Exception e) {
            e.printStackTrace();
            EntityManagerHelper.rollback();
        }
    }

    public void deleteById(ID o) {
        EntityManager em =  EntityManagerHelper.getEntityManager();

        try {
            EntityManagerHelper.beginTransaction();
            if (findById(o).isPresent()) {
                T t = findById(o).get();
                em.remove(t);
            }
            EntityManagerHelper.commit();
        } catch (Exception e) {
            e.printStackTrace();
            EntityManagerHelper.rollback();
        }
    }
}
