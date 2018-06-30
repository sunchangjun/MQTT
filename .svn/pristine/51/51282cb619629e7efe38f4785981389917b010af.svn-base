package framework.entity.po.auth;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_auth_resource")
public class Resource implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "resource_id", unique = true, nullable = false)
	private Long resourceId;

	@Column(name = "resource_type_id", insertable = false, updatable = false)
	private Long resourceTypeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_type_id")
	private ResourceType resourceType;

	@Column(name = "url", nullable = false, length = 500)
	private String url;

	@Column(name = "title", length = 50)
	private String title;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "resource")
	private List<ResourceAuthority> resourceAuthorityList;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Long getResourceTypeId() {
		return resourceTypeId;
	}

	public void setResourceTypeId(Long resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<ResourceAuthority> getResourceAuthorityList() {
		return resourceAuthorityList;
	}

	public void setResourceAuthorityList(List<ResourceAuthority> resourceAuthorityList) {
		this.resourceAuthorityList = resourceAuthorityList;
	}

}