/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.http.service.hiscore;

import java.io.IOException;
import net.runelite.http.api.RuneliteAPI;
import net.runelite.http.api.hiscore.*;
import net.runelite.http.service.HiscoreEndpointEditor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hiscore")
public class HiscoreService
{
	private static final Logger logger = LoggerFactory.getLogger(HiscoreService.class);

	private static final HttpUrl RUNESCAPE_NORMAL_HISCORE_SERVICE = HttpUrl.parse("http://services.runescape.com/m=hiscore_oldschool/index_lite.ws");
	private static final HttpUrl RUNESCAPE_IRONMAN_HISCORE_SERVICE = HttpUrl.parse("http://services.runescape.com/m=hiscore_oldschool_ironman/index_lite.ws");
	private static final HttpUrl RUNESCAPE_HARDCORE_IRONMAN_HISCORE_SERVICE = HttpUrl.parse("http://services.runescape.com/m=hiscore_oldschool_hardcore_ironman/index_lite.ws");
	private static final HttpUrl RUNESCAPE_ULTIMATE_IRONMAN_HISCORE_SERVICE = HttpUrl.parse("http://services.runescape.com/m=hiscore_oldschool_ultimate/index_lite.ws");
	private static final HttpUrl RUNESCAPE_DEADMAN_HISCORE_SERVICE = HttpUrl.parse("http://services.runescape.com/m=hiscore_oldschool_deadman/index_lite.ws");
	private static final HttpUrl RUNESCAPE_SEASONAL_DEADMAN_HISCORE_SERVICE = HttpUrl.parse("http://services.runescape.com/m=hiscore_oldschool_seasonal/index_lite.ws");

	private HttpUrl testUrl;

	private HiscoreResultBuilder lookupUsername(String username, HiscoreEndpoint endpoint) throws IOException
	{
		HttpUrl url;

		// I don't like this, there must be a better way to set the URL for the test
		if (testUrl != null)
		{
			url = testUrl;
		}
		else
		{
			switch (endpoint)
			{
				case IRONMAN:
					url = RUNESCAPE_IRONMAN_HISCORE_SERVICE;
					break;
				case HARDCORE_IRONMAN:
					url = RUNESCAPE_HARDCORE_IRONMAN_HISCORE_SERVICE;
					break;
				case ULTIMATE_IRONMAN:
					url = RUNESCAPE_ULTIMATE_IRONMAN_HISCORE_SERVICE;
					break;
				case DEADMAN:
					url = RUNESCAPE_DEADMAN_HISCORE_SERVICE;
					break;
				case SEASONAL_DEADMAN:
					url = RUNESCAPE_SEASONAL_DEADMAN_HISCORE_SERVICE;
					break;
				default:
					url = RUNESCAPE_NORMAL_HISCORE_SERVICE;
					break;
			}
		}

		HttpUrl hiscoreUrl = url.newBuilder()
			.addQueryParameter("player", username)
			.build();

		logger.info("Built URL {}", hiscoreUrl);

		Request okrequest = new Request.Builder()
			.url(hiscoreUrl)
			.build();

		Response okresponse = RuneliteAPI.CLIENT.newCall(okrequest).execute();
		String responseStr;

		try (ResponseBody body = okresponse.body())
		{
			responseStr = body.string();
		}

		CSVParser parser = CSVParser.parse(responseStr, CSVFormat.DEFAULT);

		HiscoreResultBuilder hiscoreBuilder = new HiscoreResultBuilder();
		hiscoreBuilder.setPlayer(username);

		int count = 0;

		for (CSVRecord record : parser.getRecords())
		{
			if (count++ >= HiscoreSkill.values().length)
			{
				logger.warn("Jagex Hiscore API returned unexpected data");
				break; // rest is other things?
			}

			// rank, level, experience
			int rank = Integer.parseInt(record.get(0));
			int level = Integer.parseInt(record.get(1));

			// items that are not skills do not have an experience parameter
			long experience = -1;
			if (record.size() == 3)
			{
				experience = Long.parseLong(record.get(2));
			}

			Skill skill = new Skill(rank, level, experience);
			hiscoreBuilder.setNextSkill(skill);
		}

		return hiscoreBuilder;
	}

	@RequestMapping("/{endpoint}")
	public HiscoreResult lookup(@PathVariable HiscoreEndpoint endpoint, @RequestParam String username) throws IOException
	{
		HiscoreResultBuilder result = lookupUsername(username, endpoint);
		return result.build();
	}

	@RequestMapping("/{endpoint}/{skillName}")
	public SingleHiscoreSkillResult singleSkillLookup(@PathVariable HiscoreEndpoint endpoint, @PathVariable String skillName, @RequestParam String username) throws IOException
	{
		HiscoreSkill skill = HiscoreSkill.valueOf(skillName.toUpperCase());

		// RS api only supports looking up all stats
		HiscoreResultBuilder result = lookupUsername(username, endpoint);

		// Find the skill to return
		Skill requested = result.getSkill(skill.ordinal());

		SingleHiscoreSkillResult skillResult = new SingleHiscoreSkillResult();
		skillResult.setPlayer(username);
		skillResult.setSkillName(skillName);
		skillResult.setSkill(requested);

		return skillResult;
	}

	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
		binder.registerCustomEditor(HiscoreEndpoint.class, new HiscoreEndpointEditor());
	}

	public void setUrl(HiscoreEndpoint endpoint)
	{

	}

	public HttpUrl getTestUrl()
	{
		return testUrl;
	}

	public void setTestUrl(HttpUrl url)
	{
		this.testUrl = url;
	}
}
