select * from device_offline_msg_info a
                  join device_offline_msg_info b
                       on a.device_id =b.device_id
                           and a.id <> b.id
;
#查询表中某个字段相同的行