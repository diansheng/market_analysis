CREATE TABLE `auctionhall` (
  `auc` bigint(20) unsigned NOT NULL,
  `item` int(10) unsigned NOT NULL,
  `bid` bigint(15) unsigned DEFAULT NULL,
  `buyout` bigint(15) unsigned NOT NULL,
  `timeStamp` timestamp NULL DEFAULT NULL,
  KEY `idx_auctionhall_auc` (`auc`),
  KEY `idx_auctionhall_item` (`item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
