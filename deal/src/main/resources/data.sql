insert into deal (created_date, last_modified_date, deal_image, description, hit, is_deal, price, title, local_id, category_id, member_id)
values (now(), now(), 'https://uniqon-bucket.s3.ap-northeast-2.amazonaws.com/startup/95101c8f-0719-4e15-8a2f-15461fb4b877uniqonlogo4.png', '설명1', 2, false, 2000, '제목1', 1, 8, 1),
       (now(), now(), 'https://uniqon-bucket.s3.ap-northeast-2.amazonaws.com/startup/ac3f047b-c782-4fe7-b2de-0d22e5892179uniqonlogo1.png', '설명2', 5, false, 6000, '제목2', 2, 8, 2),
       (now(), now(), 'https://uniqon-bucket.s3.ap-northeast-2.amazonaws.com/startup/ac3f047b-c782-4fe7-b2de-0d22e5892179uniqonlogo2.png', '설명3', 3, false, 5000, '제목3', 3, 8, 3),
       (now(), now(), 'https://uniqon-bucket.s3.ap-northeast-2.amazonaws.com/startup/ac3f047b-c782-4fe7-b2de-0d22e5892179uniqonlogo3.png', '설명4', 10, false, 6000, '제목4', 950, 8, 4),
       (now(), now(), 'https://uniqon-bucket.s3.ap-northeast-2.amazonaws.com/startup/ac3f047b-c782-4fe7-b2de-0d22e5892179uniqonlogo1.png', '설명5', 6, false, 6000, '제목5', 4, 9, 5);

insert into deal_favorite (created_date, last_modified_date, is_favorite, deal_id, member_id)
values (now(), now(), true, 1, 1),
       (now(), now(), true, 2, 1),
       (now(), now(), true, 4, 1),
       (now(), now(), true, 5, 1),
       (now(), now(), true, 1, 2),
       (now(), now(), true, 3, 2),
       (now(), now(), true, 4, 3),
       (now(), now(), false, 2, 4),
       (now(), now(), false, 4, 4),
       (now(), now(), true, 5, 4),
       (now(), now(), true, 3, 5),
       (now(), now(), true, 4, 5),
       (now(), now(), true, 5, 5),
       (now(), now(), true, 2, 6);

insert into deal_history (created_date, last_modified_date, deal_status, promise_location, promise_time, deal_id, member_id)
values (now(), now(), 'AWAITER', null, null, 2, 1),
       (now(), now(), 'AWAITER', null, null, 3, 1),
       (now(), now(), 'RESERVER', null, null, 5, 1);
