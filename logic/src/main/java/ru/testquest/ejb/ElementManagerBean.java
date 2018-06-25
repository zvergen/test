package ru.testquest.ejb;

import ru.testquest.domain.Element;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class ElementManagerBean {

    @PersistenceContext(name = "sample_PU")
    private EntityManager entityManager;

    public List<Element> getElementList(long id) {
        TypedQuery<Element> query = entityManager.createNamedQuery(Element.ELEMENT_LIST_BY_ID, Element.class);
        query.setParameter("parentId", id);
        List<Element> elements = query.getResultList();
        if (elements.isEmpty()) {
            return null;
        }
        for (Element element : elements) {
            element.setChildrens(getElementList(element.getId()));
        }
        return elements;
    }

    public Element getElementById(long id) {
        Element element = entityManager.find(Element.class, id);
        if (element == null) {
            return null;
        }
        return element;
    }

    public void createElement(Element element) {
        entityManager.persist(element);
    }

    public Element updateElementData(Element newData) {
        Element element = entityManager.find(Element.class, newData.getId());
        if (element == null) {
            return null;
        }
        element.setName(newData.getName());
        element.setDescription(newData.getDescription());
        entityManager.merge(element);
        return element;
    }

    public boolean deleteElement(long id) {
        Element element = entityManager.find(Element.class, id);
        if (element == null) {
            return false;
        }
        TypedQuery<Element> query = entityManager.createNamedQuery(Element.ELEMENT_LIST_BY_ID, Element.class);
        query.setParameter("parentId", element.getId());
        List<Element> childElements = query.getResultList();
        if (childElements.isEmpty()) {
            entityManager.remove(element);
            return true;
        }
        for (Element childElement : childElements) {
            deleteElement(childElement.getId());
        }
        entityManager.remove(element);
        return true;
    }
}
