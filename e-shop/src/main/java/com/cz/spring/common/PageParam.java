package com.cz.spring.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cz.spring.common.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageParam extends Page {
	private static final Pattern humpPattern = Pattern.compile("[A-Z]");
	private static final boolean needToLine = true;
	private static final String FILED_PAGE = "page";
	private static final String FILED_LIMIT = "limit";
	private static final String FILED_SORT = "sort";
	private static final String FILED_ORDER = "order";
	private static final String VALUE_ORDER_ASC = "asc";
	private static final String VALUE_ORDER_DESC = "desc";
	private HashMap<String, Object> pageData;

	public PageParam() {
		super();
	}

	public PageParam(HttpServletRequest request) {
		String fOrder = null, fSort = null;
		HashMap map = new HashMap();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String value = request.getParameter(name);
			if (StringUtil.isBlank(value)) {
				value = null;
			}
			if (FILED_PAGE.equals(name)) {
				if (value != null) {
					setPage(Long.parseLong(value));
				}
			} else if (FILED_LIMIT.equals(name)) {
				if (value != null) {
					setLimit(Long.parseLong(value));
				}
			} else if (FILED_ORDER.equals(name)) {
				if (value != null) {
					fOrder = value;
				}
			} else if (FILED_SORT.equals(name)) {
				if (value != null) {
					fSort = (needToLine ? humpToLine(value) : value);
				}
			} else {
				map.put(name, value);
			}
		}
		setPageData(map);

		if (VALUE_ORDER_DESC.equals(fOrder)) {
			if (fSort != null) {
				addOrderDesc(fSort);
			}
		} else if (VALUE_ORDER_ASC.equals(fOrder)) {
			if (fSort != null) {
				addOrderAsc(fSort);
			}
		}
	}

	public long getPage() {
		return getCurrent();
	}

	public void setPage(long page) {
		setCurrent(page);
	}

	public long getLimit() {
		return getSize();
	}

	public void setLimit(long limit) {
		setSize(limit);
	}

	public HashMap<String, Object> getPageData() {
		return pageData;
	}

	public void setPageData(HashMap<String, Object> data) {
		this.pageData = data;
	}

	public PageParam put(String key, Object value) {
		pageData.put(key, value);
		return this;
	}

	public String getString(String key) {
		Object o = pageData.get(key);
		if (o == null) {
			return null;
		}
		return (String) o;
	}

	public Integer getInt(String key) {
		Object o = pageData.get(key);
		if (o == null) {
			return null;
		}
		return Integer.parseInt((String) o);
	}

	public Float getFloat(String key) {
		Object o = pageData.get(key);
		if (o == null) {
			return null;
		}
		return Float.parseFloat((String) o);
	}

	public Double getDouble(String key) {
		Object o = pageData.get(key);
		if (o == null) {
			return null;
		}
		return Double.parseDouble((String) o);
	}

	public Boolean getBoolean(String key) {
		Object o = pageData.get(key);
		if (o == null) {
			return null;
		}
		return Boolean.parseBoolean((String) o);
	}

	public Object get(String key) {
		return pageData.get(key);
	}

	public QueryWrapper getWrapper() {
		QueryWrapper queryWrapper = new QueryWrapper();
		Iterator<String> iterator = pageData.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = pageData.get(key);
			if (value != null) {
				queryWrapper.like(needToLine ? humpToLine(key) : key, value);
			}
		}
		return queryWrapper;
	}

	public PageParam setDefaultOrder(String[] ascs, String[] descs) {
		if (arrayIsEmpty(getOrderDescs()) && arrayIsEmpty(getOrderAscs())) {
			addOrderAsc(ascs);
			addOrderDesc(descs);
		}
		return this;
	}

	public PageParam addOrderAsc(String... ascs) {
		if (ascs != null) {
			List<String> tAscs = new ArrayList<>();
			if (getOrderAscs() != null) {
				tAscs.addAll(arrayToList(getOrderAscs()));
			}
			for (int i = 0; i < ascs.length; i++) {
				if (!tAscs.contains(ascs[i])) {
					tAscs.add(ascs[i]);
				}
			}
			setOrderAsc(listToArray(tAscs));
		}
		return this;
	}

	public PageParam addOrderDesc(String... descs) {
		if (descs != null) {
			List<String> tDescs = new ArrayList<>();
			if (getOrderDescs() != null) {
				tDescs.addAll(arrayToList(getOrderDescs()));
			}
			for (int i = 0; i < descs.length; i++) {
				if (!tDescs.contains(descs[i])) {
					tDescs.add(descs[i]);
				}
			}
			setOrderDesc(listToArray(tDescs));
		}
		return this;
	}

	public PageParam setOrderAsc(String... ascs) {
		if (ascs != null) {
			List<OrderItem> orderItems = new ArrayList<>();
			for (int i = 0; i < ascs.length; i++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setAsc(true);
				orderItem.setColumn(ascs[i]);
				orderItems.add(orderItem);
			}
			setOrders(orderItems);
		}
		return this;
	}

	public PageParam setOrderDesc(String... descs) {
		if (descs != null) {
			List<OrderItem> orderItems = new ArrayList<>();
			for (int i = 0; i < descs.length; i++) {
				OrderItem orderItem = new OrderItem();
				orderItem.setAsc(false);
				orderItem.setColumn(descs[i]);
				orderItems.add(orderItem);
			}
			setOrders(orderItems);
		}
		return this;
	}

	private static String humpToLine(String str) {
		Matcher matcher = humpPattern.matcher(str);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String[] listToArray(List<String> list) {
		String[] strs = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strs[i] = list.get(i);
		}
		return strs;
	}

	private List<String> arrayToList(String[] array) {
		List<String> strList = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
			strList.add(array[i]);
		}
		return strList;
	}

	private boolean arrayIsEmpty(String[] array) {
		return (array == null || array.length == 0);
	}

	/**
	 *
	 * @return
	 */
	public String[] getOrderAscs() {
		List<String> ascs = new ArrayList<>();
		List<OrderItem> orders = getOrders();
		for (OrderItem order : orders) {
			if (order.isAsc()) {
				ascs.add(order.getColumn());
			}
		}
		return listToArray(ascs);
	}

	/**
	 *
	 * @return
	 */
	public String[] getOrderDescs() {
		List<String> descs = new ArrayList<>();
		List<OrderItem> orders = getOrders();
		for (OrderItem order : orders) {
			if (!order.isAsc()) {
				descs.add(order.getColumn());
			}
		}
		return listToArray(descs);
	}

}
