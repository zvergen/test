package ru.testquest.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "t_element")
@NamedQueries({
        @NamedQuery(name = Element.ELEMENT_LIST_BY_ID,
                query = "SELECT e FROM Element e WHERE e.parentId = :parentId",
                lockMode = LockModeType.PESSIMISTIC_WRITE)
})
public class Element {

    public static final String ELEMENT_LIST_BY_ID = "Element.elementListById";

    @Id
    @SequenceGenerator(name = "hibernateSeq", sequenceName = "HIBERNATE_SEQUENCE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernateSeq")
    private long id;
    private String name;
    @Column(length = 2000)
    private String description;
    private long parentId;
    @Transient
    private List<Element> childrens;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parent_id) {
        this.parentId = parent_id;
    }

    public List<Element> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<Element> childrens) {
        this.childrens = childrens;
    }
}
