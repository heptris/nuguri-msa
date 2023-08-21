INSERT INTO hobby(hobby_id,created_date,last_modified_date,content,cur_num,end_date,fee,high_age_limit,hobby_image,is_closed,max_num,meeting_place,row_age_limit,sex_limit,title,local_id,category_id,member_id) VALUES (1,'2022-11-18 15:58:26.103797','2022-11-18 15:58:26.103797','1시간정도 같이 걸으면서 운동하실분 구해요',1,'2022-12-18 06:49:37.469000',0,50,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/dd4432a3-b424-4de1-bd83-991a49416629nugurijogging.jpg',_binary '\0',5,'한강공원',20,'X','한강 조깅 운동',384,2,1),(2,'2022-11-18 16:02:34.349171','2022-11-18 16:02:34.349171','퇴근하고 같이 족발먹으로 가요!',1,'2022-11-21 18:49:37.469000',20000,50,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/6a8f648b-8b12-4370-9852-f5c01a8d6b7cnugurifood.jpg',_binary '\0',5,'청운족발집',20,'X','족발 모임',1,4,1),(4,'2022-11-18 16:10:35.952221','2022-11-18 16:10:35.952221','매주 새로운 사람과 여행을 떠나요',1,'2022-11-19 09:00:37.469000',100000,30,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/37bf5d8a-a57b-4581-8953-93e214102e79nuguritrip.jpg',_binary '\0',3,'서울역에서 출발',20,'w','청주여행 가실분',162,6,2),(5,'2022-11-18 16:11:23.035395','2022-11-18 16:11:23.035395','매주 새로운 사람과 여행을 떠나요',1,'2022-11-26 09:00:37.469000',100000,30,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/ae0485ab-a38b-412c-ae31-409de61edd92nuguritrip2.jpg',_binary '\0',3,'서울역에서 출발',20,'w','부산여행 가실분',162,6,2),(6,'2022-11-18 16:14:33.112946','2022-11-18 16:14:33.112946','매주 새로운 사람과 여행을 떠나요',1,'2022-12-04 09:00:37.469000',100000,30,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/b8879417-3733-4743-bb34-7811205d74a5nuguritrip3.jpg',_binary '\0',3,'서울역에서 출발',20,'w','수원여행 가실분',162,6,2),(7,'2022-11-18 16:28:21.035823','2022-11-18 16:28:21.035823','청운동 전시회 투어 첫번째',1,'2022-11-22 09:00:37.469000',30000,100,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/5af27849-fd97-4a41-b85a-1e944493da0enuguri%EC%A0%84%EC%8B%9C%ED%9A%8C1.png',_binary '\0',5,'청운광장',0,'w','청운동 전시회 투어',1,3,4),(8,'2022-11-18 16:28:35.211361','2022-11-18 16:28:35.211361','청운동 전시회 투어 두번째',1,'2022-11-23 09:00:37.469000',30000,100,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/fb5e85ac-a960-4980-9bed-3c26f0ec6fbdnuguri%EC%A0%84%EC%8B%9C%ED%9A%8C2.png',_binary '\0',5,'청운광장',0,'w','청운동 전시회 투어',1,3,4),(9,'2022-11-18 16:28:44.426229','2022-11-18 16:28:44.426229','청운동 전시회 투어 세번째',1,'2022-11-23 09:00:37.469000',30000,100,'https://nuguri-bucket.s3.ap-northeast-2.amazonaws.com/hobbyImage/33bf8451-d8af-4bc8-bb72-d6ecf6055996nuguri%EC%A0%84%EC%8B%9C%ED%9A%8C3.png',_binary '\0',5,'청운광장',0,'w','청운동 전시회 투어',1,3,4);

INSERT INTO hobby_favorite(hobby_favorite_id,is_favorite,hobby_id,member_id) VALUES (1,_binary '',1,1),(2,_binary '',2,1),(3,_binary '',4,1),(4,_binary '',1,3),(5,_binary '',2,3),(6,_binary '',4,3),(7,_binary '',5,3),(8,_binary '',6,3),(9,_binary '',1,4),(10,_binary '',2,4),(11,_binary '',5,4),(12,_binary '',6,4),(13,_binary '',7,4),(14,_binary '',8,4);

INSERT INTO hobby_history(hobby_history_id,created_date,last_modified_date,approve_status,is_promoter,hobby_id,member_id) VALUES (1,'2022-11-18 15:58:26.136804','2022-11-18 15:58:26.136804','APPROVE',_binary '',1,1),(2,'2022-11-18 16:02:34.352170','2022-11-18 16:02:34.352170','APPROVE',_binary '',2,1),(4,'2022-11-18 16:10:35.956222','2022-11-18 16:10:35.956222','APPROVE',_binary '',4,2),(5,'2022-11-18 16:11:23.038396','2022-11-18 16:11:23.038396','APPROVE',_binary '',5,2),(6,'2022-11-18 16:14:33.116946','2022-11-18 16:14:33.116946','APPROVE',_binary '',6,2),(7,'2022-11-18 16:18:34.097219','2022-11-18 16:18:34.097219','READY',_binary '\0',4,1),(8,'2022-11-18 16:18:36.850811','2022-11-18 16:21:25.737804','REJECT',_binary '\0',5,1),(9,'2022-11-18 16:18:38.022102','2022-11-18 16:21:08.965913','APPROVE',_binary '\0',6,1),(10,'2022-11-18 16:19:42.402592','2022-11-18 16:19:42.402592','READY',_binary '\0',1,3),(11,'2022-11-18 16:19:43.633244','2022-11-18 16:19:43.633244','READY',_binary '\0',2,3),(12,'2022-11-18 16:19:44.451294','2022-11-18 16:19:44.451294','READY',_binary '\0',4,3),(13,'2022-11-18 16:19:45.507741','2022-11-18 16:19:45.507741','READY',_binary '\0',5,3),(14,'2022-11-18 16:19:46.251194','2022-11-18 16:19:46.251194','READY',_binary '\0',6,3),(15,'2022-11-18 16:28:21.044825','2022-11-18 16:28:21.044825','APPROVE',_binary '',7,4),(16,'2022-11-18 16:28:35.215899','2022-11-18 16:28:35.215899','APPROVE',_binary '',8,4),(17,'2022-11-18 16:28:44.430230','2022-11-18 16:28:44.430230','APPROVE',_binary '',9,4);
