package com.hkgoodvision.gvpos.dao.vo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * 帖子列表实体类
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class RewardList extends Entity {

	private int pageSize = 20;
	private int count;
	private List<Reward> rewardList = new ArrayList<Reward>();

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Reward> getRewardList() {
		return rewardList;
	}

	public void setRewardList(List<Reward> serviceList) {
		this.rewardList = serviceList;
	}

	public static RewardList parse(InputStream inputStream) throws IOException, JSONException {
		RewardList memberShipList = new RewardList();
		Reward reward = new Reward();
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		StringBuilder responseStrBuilder = new StringBuilder();

		String inputStr;
		while ((inputStr = streamReader.readLine()) != null)
			responseStrBuilder.append(inputStr);

		JSONObject jObject = new JSONObject(responseStrBuilder.toString());

		JSONArray jArray = null;
		if (jObject.isNull("earnpoint")) {
			return memberShipList;
		}
		jArray = jObject.getJSONArray("earnpoint");

		// get the List Array
		int i = 0;
		for (i = 0; i < jArray.length(); i++) {
			JSONObject oneObject = jArray.getJSONObject(i);
			
			if (oneObject == null)
				continue;
			
			// Pulling items from the array
			String reward_id = oneObject.getString("reward_id");
			String title = oneObject.getString("title");
			String point = oneObject.getString("point");
			String photo = oneObject.getString("photo");

			Log.d("Reward",reward_id + ":" + title + ":" + point + "|" + photo);

			reward = new Reward();

			reward.id = Integer.parseInt(reward_id);
			reward.setRewardId(Integer.parseInt(reward_id));
			reward.setTitle(title);
			reward.setPoint(Integer.parseInt(point));
			reward.setPhoto(photo);


			memberShipList.getRewardList().add(reward);
		}

		memberShipList.setCount(i);

		return memberShipList;

	}
}
