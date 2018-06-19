package ru.testquest.ejb;

import ru.testquest.domain.Element;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateful
public class ElementDeleteBean {

    @PersistenceContext(name = "sample_PU")
    private EntityManager entityManager;

    public boolean deleteElement(long id) {
        Element element = entityManager.find(Element.class, id);
        if (element == null) {
            return false;
        }
        TypedQuery<Element> query = entityManager.createNamedQuery(Element.ALL_CHILD_ELEMENT_BY_ID, Element.class);
        query.setParameter("parentId", element.getId());
        List<Element> childElements = query.getResultList();
        if (childElements.isEmpty()) {
            entityManager.getTransaction().begin();
            entityManager.remove(element);
            entityManager.getTransaction().commit();
            return true;
        } else
        for (Element childElement : childElements) {
            deleteElement(childElement.getId());
        }
        entityManager.getTransaction().begin();
        entityManager.remove(element);
        entityManager.getTransaction().commit();
        return true;
    }

}
