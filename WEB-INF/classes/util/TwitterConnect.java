package util;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import bean.Advertisement;
import service.AdvertisementService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwitterConnect {
	private static final String consumerKeyStr = "Tg6kN9gFQ27G4Y2g90futgxjT";
	private static final String consumerSecretStr = "w07OWsElDu0gXfqEKrE5F5AtvnQ2eH5eL3LemksfgJgXNpXxPw";
	private static final String accessTokenStr = "800850570307371009-or0aZlHmfOfkU9MfM7ypDoBwLudeKUy";
	private static final String accessTokenSecretStr = "0SPSZs4hQrek9YiQhdQ2oIKddhMPtUQhMVwUHw69WDCxy";
	private static Map<Integer, List<String>> hashTweets = new HashMap<>();
	
	public static void main(String[] args) throws TwitterException, ClassNotFoundException {
		List<String> test = getTweets(21);
		System.out.println(test);
		for (int i = 0; i < test.size(); i++) {
			System.out.println(test.get(i));
		}
	}
	
	private static List<String> getTweetsFromTwitter(int advertisementId) throws TwitterException {
		//System.out.println("Getting From Twitter.\n");
		List<String> tweets = new ArrayList<>();
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setUseSSL(true);
			cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKeyStr)
									.setOAuthConsumerSecret(consumerSecretStr)
									.setOAuthAccessToken(accessTokenStr)
									.setOAuthAccessTokenSecret(accessTokenSecretStr);

			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter4j.Twitter twitter = tf.getInstance();
			
			List<Status> bestBuyStatus = twitter.getUserTimeline("BestBuy_Deals");
			List<Status> slickDealsStatus = twitter.getUserTimeline("slickdeals");
			List<Status> newEggStatus = twitter.getUserTimeline("Newegg");
			
			ArrayList<String> bestBuyList = new ArrayList<String>();
			ArrayList<String> slickDealsList = new ArrayList<String>();
			ArrayList<String> newEggList = new ArrayList<String>();
			
			for (Status st : bestBuyStatus) {
				bestBuyList.add(st.getText());
			}

			for (Status st : slickDealsStatus) {
				slickDealsList.add(st.getText());
			}

			for (Status st : newEggStatus) {
				newEggList.add(st.getText());
			}

			ArrayList<String> total = new ArrayList<>();
			total.addAll(bestBuyList);
			total.addAll(slickDealsList);
			total.addAll(newEggList);

			AdvertisementService AD = new AdvertisementService();
			Advertisement a = AD.getAdvertisement(advertisementId);

			String searchTitle = a.getTitle();

			String[] words = { "for", "sale", "and", "used", "less", "or", "an", "the", "new", "old", "both", "of",
					"with" };
			ArrayList<String> stopWords = new ArrayList<>();

			stopWords.addAll(Arrays.asList(words));

			String[] str = searchTitle.split(" ");

			for (String x : str) {
				if (!stopWords.contains(x.toLowerCase())) {
					for (String y : total) {
						List<String> tokens = Arrays.asList(y.toLowerCase().split("[^a-zA-Z]"));
						if (tokens.contains(x.toLowerCase())) {
							tweets.add(y);
							hashTweets.put(advertisementId, tweets);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tweets;
	}

	public static List<String> getTweets(int advertisementId) throws TwitterException {
		if (hashTweets.containsKey(advertisementId)) {
			//System.out.println("Getting from HashMap.\n");
			return hashTweets.get(advertisementId);
		} else {
			List<String> tweets = getTweetsFromTwitter(advertisementId);
			if (tweets.size() > 0) {
				hashTweets.put(advertisementId, tweets);
			}
			return tweets;
		}
	}
}
