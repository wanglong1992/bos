package cn.itcast.domain.page;


import cn.itcast.domain.take_delivery.Promotion;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.List;

@XmlRootElement(name = "pageBean")
@XmlSeeAlso(value = {Promotion.class})
public class PageBean<T> {

    // 总记录数
    private Long totalElements;
    // 返回的集合
    private List<T> content;

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }


}
