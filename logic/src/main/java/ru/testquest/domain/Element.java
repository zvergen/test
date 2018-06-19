package ru.testquest.domain;

import javax.persistence.*;

@Entity
@Table(name = "t_element")
@NamedQueries({
        @NamedQuery(name = Element.ALL_ZERO_ELEMENT_LIST,
                query = "SELECT e FROM Element e WHERE e.parent_id = 0"),
        @NamedQuery(name = Element.ELEMENT_BY_ID,
                query = "SELECT e FROM Element e WHERE e.id = :id"),
        @NamedQuery(name = Element.ALL_CHILD_ELEMENT_BY_ID,
                query = "SELECT e FROM Element e WHERE e.parent_id = :parentId",
                lockMode = LockModeType.PESSIMISTIC_WRITE)
})
public class Element {

    public static final String ALL_ZERO_ELEMENT_LIST = "Element.allZeroElementList";
    public static final String ELEMENT_BY_ID = "Element.elementById";
    public static final String ALL_CHILD_ELEMENT_BY_ID = "Element.allChildElementById";

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Column(length = 2000)
    private String description;
    private long parent_id;

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

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }
}
