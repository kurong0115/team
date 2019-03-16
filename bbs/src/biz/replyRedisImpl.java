package biz;

import bean.Reply;
import redis.clients.jedis.Jedis;

public class replyRedisImpl {
	//点赞
	@SuppressWarnings("resource")
	public Long glkreply(Reply reply) {
		Jedis jedis = new Jedis("localhost", 6379);
		//判断redids中是否有  1是否在键 为topic1中. 
		if (jedis.sismember("topic" + reply.getTopicid(), reply.getUid() + "") == false) {// 判断值是否存在于键中
			jedis.sadd("topic" + reply.getTopicid(), reply.getUid() + "");
		} else {
			jedis.smove("topic" + reply.getTopicid(), "trash", reply.getUid() + "");// 将值从键中取出
																					// 加入另一个键
		}
		Long glk = jedis.scard("topic" + reply.getTopicid());
		return glk;
	}
	//获取某个贴子的点赞数
	@SuppressWarnings("resource")
	public Long gettimes(Reply reply) {
		Jedis jedis = new Jedis("localhost", 6379);
		Long glk = jedis.scard("topic" + reply.getTopicid());
		return glk;
	}
}
