/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : mypetstore

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 21/06/2020 09:41:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `userid` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `firstname` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lastname` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `addr1` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `addr2` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `state` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `zip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `country` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('j2ee', 'yourname@yourdomain.com', 'ABC', 'XYX', 'OK', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', '555-555-5555');
INSERT INTO `account` VALUES ('Peak', '', '', '', NULL, '', '', '', '', '', '', '');

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` int(1) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES ('admin', '男', '15673450108', '1619817240@qq.com', 0, '兴趣使然');

-- ----------------------------
-- Table structure for bannerdata
-- ----------------------------
DROP TABLE IF EXISTS `bannerdata`;
CREATE TABLE `bannerdata`  (
  `favcategory` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `bannername` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`favcategory`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bannerdata
-- ----------------------------
INSERT INTO `bannerdata` VALUES ('BIRDS', '<image src=\"../images/banner_birds.gif\">');
INSERT INTO `bannerdata` VALUES ('CATS', '<image src=\"../images/banner_cats.gif\">');
INSERT INTO `bannerdata` VALUES ('DOGS', '<image src=\"../images/banner_dogs.gif\">');
INSERT INTO `bannerdata` VALUES ('FISH', '<image src=\"../images/banner_fish.gif\">');
INSERT INTO `bannerdata` VALUES ('REPTILES', '<image src=\"../images/banner_reptiles.gif\">');

-- ----------------------------
-- Table structure for cart
-- ----------------------------
DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart`  (
  `userid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `itemid` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `inStock` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`userid`, `itemid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cart
-- ----------------------------
INSERT INTO `cart` VALUES ('j2ee', 'EST-16', 1, 1);
INSERT INTO `cart` VALUES ('j2ee', 'EST-18', 5, 1);
INSERT INTO `cart` VALUES ('j2ee', 'EST-19', 2, 1);
INSERT INTO `cart` VALUES ('j2ee', 'EST-4', 5, 1);
INSERT INTO `cart` VALUES ('j2ee', 'EST-6', 30, 1);
INSERT INTO `cart` VALUES ('Peak', 'ss', 22, 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `catid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `descn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`catid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('BIRDS', 'Birds', '<image src=\"../images/birds_icon.gif\"><font size=\"5\" color=\"blue\"> Birds</font>', '../images/birds_icon.gif', 'Birds');
INSERT INTO `category` VALUES ('CATS', 'Cats', '<image src=\"../images/cats_icon.gif\"><font size=\"5\" color=\"blue\"> Cats</font>', '../images/cats_icon.gif', 'Cats');
INSERT INTO `category` VALUES ('DOGS', 'Dogs', '<image src=\"../images/dogs_icon.gif\"><font size=\"5\" color=\"blue\"> Dogs</font>', '../images/dogs_icon.gif', 'Dogs');
INSERT INTO `category` VALUES ('FISH', 'Fish 1', '<image src=\"../images/fish_icon.gif\"><font size=\"5\" color=\"blue\"> Fish</font>', '../images/fish_icon.gif', 'Great Fish');
INSERT INTO `category` VALUES ('REPTILES', 'Reptiles', '<image src=\"../images/reptiles_icon.gif\"><font size=\"5\" color=\"blue\"> Reptiles</font>', '../images/reptiles_icon.gif', 'Reptiles');
INSERT INTO `category` VALUES ('Test1', 'test1', NULL, NULL, 'To test fir');
INSERT INTO `category` VALUES ('Test3', 'test3', NULL, NULL, 'To test4');
INSERT INTO `category` VALUES ('Test5', 'test5', NULL, NULL, 'To test5');
INSERT INTO `category` VALUES ('Test6', 'test6', NULL, NULL, 'To test 6th');

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `itemid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`itemid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of inventory
-- ----------------------------
INSERT INTO `inventory` VALUES ('AAA-01', 100);
INSERT INTO `inventory` VALUES ('BBB-03', 200);
INSERT INTO `inventory` VALUES ('CCC-05', 500);
INSERT INTO `inventory` VALUES ('EST-1', 10000);
INSERT INTO `inventory` VALUES ('EST-10', 10000);
INSERT INTO `inventory` VALUES ('EST-11', 10000);
INSERT INTO `inventory` VALUES ('EST-12', 10000);
INSERT INTO `inventory` VALUES ('EST-13', 10000);
INSERT INTO `inventory` VALUES ('EST-14', 10000);
INSERT INTO `inventory` VALUES ('EST-15', 10000);
INSERT INTO `inventory` VALUES ('EST-16', 10000);
INSERT INTO `inventory` VALUES ('EST-17', 10000);
INSERT INTO `inventory` VALUES ('EST-18', 10000);
INSERT INTO `inventory` VALUES ('EST-19', 10000);
INSERT INTO `inventory` VALUES ('EST-2', 10000);
INSERT INTO `inventory` VALUES ('EST-20', 10000);
INSERT INTO `inventory` VALUES ('EST-21', 10000);
INSERT INTO `inventory` VALUES ('EST-22', 10000);
INSERT INTO `inventory` VALUES ('EST-23', 10000);
INSERT INTO `inventory` VALUES ('EST-24', 10000);
INSERT INTO `inventory` VALUES ('EST-25', 10000);
INSERT INTO `inventory` VALUES ('EST-26', 10000);
INSERT INTO `inventory` VALUES ('EST-27', 10000);
INSERT INTO `inventory` VALUES ('EST-28', 10000);
INSERT INTO `inventory` VALUES ('EST-3', 10000);
INSERT INTO `inventory` VALUES ('EST-4', 10000);
INSERT INTO `inventory` VALUES ('EST-5', 10000);
INSERT INTO `inventory` VALUES ('EST-6', 9994);
INSERT INTO `inventory` VALUES ('EST-7', 10000);
INSERT INTO `inventory` VALUES ('EST-8', 10000);
INSERT INTO `inventory` VALUES ('EST-9', 10000);

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `itemid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stockquantity` int(10) NULL DEFAULT NULL,
  `productid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `listprice` decimal(10, 2) NULL DEFAULT NULL,
  `unitcost` decimal(10, 2) NULL DEFAULT NULL,
  `supplier` int(11) NULL DEFAULT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr1` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr2` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr3` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr4` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `attr5` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`itemid`) USING BTREE,
  INDEX `fk_item_2`(`supplier`) USING BTREE,
  INDEX `itemProd`(`productid`) USING BTREE,
  CONSTRAINT `fk_item_1` FOREIGN KEY (`productid`) REFERENCES `product` (`productid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_item_2` FOREIGN KEY (`supplier`) REFERENCES `supplier` (`suppid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES ('AAA-01', 100, 'FL-DLH-02', 29.50, 28.40, 1, 'T', 'Female', '', '', NULL, NULL);
INSERT INTO `item` VALUES ('BBB-03', 200, 'AV-CB-01', NULL, NULL, 1, 'T', '', '', '', NULL, NULL);
INSERT INTO `item` VALUES ('CCC-05', 10, 'FI-SW-02', 66.04, 43.92, 2, 'P', 'W', 'B', 'R', NULL, NULL);
INSERT INTO `item` VALUES ('EST-1', 10, 'FI-SW-01', 16.50, 10.00, 1, 'P', 'Large', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-10', 10, 'K9-DL-01', 18.50, 12.00, 1, 'P', 'Spotted Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-11', 10, 'RP-SN-01', 18.50, 12.00, 1, 'P', 'Venomless', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-12', 10, 'RP-SN-01', 18.50, 12.00, 1, 'P', 'Rattleless', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-13', 10, 'RP-LI-02', 18.50, 12.00, 1, 'P', 'Green Adult', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-14', 10, 'FL-DSH-01', 58.50, 12.00, 1, 'P', 'Tailless', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-15', 10, 'FL-DSH-01', 23.50, 12.00, 1, 'P', 'With tail', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-16', 10, 'FL-DLH-02', 93.50, 12.00, 1, 'P', 'Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-17', 10, 'FL-DLH-02', 93.50, 12.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-18', 10, 'AV-CB-01', 193.50, 92.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-19', 10, 'AV-SB-02', 15.50, 2.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-2', 10, 'FI-SW-01', 16.50, 10.00, 1, 'P', 'Small', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-20', 10, 'FI-FW-02', 5.50, 2.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-21', 10, 'FI-FW-02', 5.29, 1.00, 1, 'P', 'Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-22', 10, 'K9-RT-02', 135.50, 100.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-23', 10, 'K9-RT-02', 145.49, 100.00, 1, 'P', 'Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-24', 10, 'K9-RT-02', 255.50, 92.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-25', 10, 'K9-RT-02', 325.29, 90.00, 1, 'P', 'Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-26', 10, 'K9-CW-01', 125.50, 92.00, 1, 'P', 'Adult Male', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-27', 10, 'K9-CW-01', 155.29, 90.00, 1, 'P', 'Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-28', 10, 'K9-RT-01', 155.29, 90.00, 1, 'P', 'Adult Female', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-3', 10, 'FI-SW-02', 18.50, 12.00, 1, 'P', 'Toothless', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-4', 10, 'FI-FW-01', 18.50, 12.00, 1, 'P', 'Spotted', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-5', 10, 'FI-FW-01', 18.50, 12.00, 1, 'P', 'Spotless', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-6', 10, 'K9-BD-01', 18.50, 12.00, 1, 'P', 'Male Adult', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-7', 10, 'K9-BD-01', 18.50, 12.00, 1, 'P', 'Female Puppy', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-8', 10, 'K9-PO-02', 18.50, 12.00, 1, 'P', 'Male Puppy', NULL, NULL, NULL, NULL);
INSERT INTO `item` VALUES ('EST-9', 10, 'K9-DL-01', 18.50, 12.00, 1, 'P', 'Spotless Male Puppy', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for lineitem
-- ----------------------------
DROP TABLE IF EXISTS `lineitem`;
CREATE TABLE `lineitem`  (
  `orderid` int(11) NOT NULL,
  `linenum` int(11) NOT NULL,
  `itemid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quantity` int(11) NOT NULL,
  `unitprice` decimal(10, 2) NOT NULL,
  PRIMARY KEY (`orderid`, `linenum`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of lineitem
-- ----------------------------
INSERT INTO `lineitem` VALUES (1001, 2, 'EST-5', 4, 12.00);
INSERT INTO `lineitem` VALUES (1001, 7, 'EST-3', 5, 12.00);
INSERT INTO `lineitem` VALUES (1001, 8, 'EST-1', 4, 12.00);
INSERT INTO `lineitem` VALUES (1003, 1, 'EST-7', 3, 12.00);
INSERT INTO `lineitem` VALUES (1003, 3, 'EST-23', 5, 12.00);
INSERT INTO `lineitem` VALUES (1003, 4, 'EST-24', 1, 12.00);
INSERT INTO `lineitem` VALUES (1013, 0, 'EST-6', 6, 12.00);
INSERT INTO `lineitem` VALUES (1013, 1, 'EST-19', 2, 12.00);
INSERT INTO `lineitem` VALUES (1013, 6, 'EST-18', 3, 12.00);

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `logUserId` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `logInfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 10:29:00     http://localhost:8080/viewItem?itemId=EST-18浏览（EST-18)商品');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 15:17:40     http://localhost:8080/addItemToCart?workingItemId=EST-6添加商品（EST-6)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 15:18:03     http://localhost:8080/addItemToCart?workingItemId=EST-6添加商品（EST-6)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 16:03:32     http://localhost:8080/addItemToCart?workingItemId=EST-7添加商品（EST-7)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 16:14:18     http://localhost:8080/addItemToCart?workingItemId=EST-14添加商品（EST-14)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 16:21:08     http://localhost:8080/addItemToCart?workingItemId=EST-25添加商品（EST-25)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 16:21:15     http://localhost:8080/removeItemFromCart?cartItem=EST-25从购物车中移除商品（EST-25)');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:12:43     http://localhost:8080/addItemToCart?workingItemId=EST-15添加商品（EST-15)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:14:26     http://localhost:8080/addItemToCart?workingItemId=EST-16添加商品（EST-16)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:19:08     http://localhost:8080/addItemToCart?workingItemId=EST-27添加商品（EST-27)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:20:42     http://localhost:8080/addItemToCart?workingItemId=EST-2添加商品（EST-2)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:22:19     http://localhost:8080/addItemToCart?workingItemId=EST-19添加商品（EST-19)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:22:50     http://localhost:8080/addItemToCart?workingItemId=EST-19添加商品（EST-19)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:25:46     http://localhost:8080/addItemToCart?workingItemId=EST-6添加商品（EST-6)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:26:04     http://localhost:8080/addItemToCart?workingItemId=EST-4添加商品（EST-4)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:27:29     http://localhost:8080/addItemToCart?workingItemId=EST-25添加商品（EST-25)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 17:33:37     http://localhost:8080/removeItemFromCart?cartItem=EST-2从购物车中移除商品（EST-2)');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 22:06:30     http://localhost:8080/removeItemFromCart?cartItem=EST-25从购物车中移除商品（EST-25)');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 22:06:36     http://localhost:8080/addItemToCart?workingItemId=EST-13添加商品（EST-13)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-21 22:09:05     http://localhost:8080/addItemToCart?workingItemId=EST-6添加商品（EST-6)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-25 18:10:43     http://localhost:8080/viewItem?itemId=EST-7浏览（EST-7)商品');
INSERT INTO `log` VALUES ('j2ee', '2019-10-25 18:10:45     http://localhost:8080/addItemToCart?workingItemId=EST-7添加商品（EST-7)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-25 18:20:18     http://localhost:8080/addItemToCart?workingItemId=EST-19添加商品（EST-19)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-27 20:22:40     http://localhost:8080/viewListOrder?username=j2ee 查看订单 [org.csu.mypetstore.domain.Order@746399d7]');
INSERT INTO `log` VALUES ('j2ee', '2019-10-27 20:23:29     http://localhost:8080/viewListOrder?username=j2ee 查看订单 [org.csu.mypetstore.domain.Order@65cb6074]');
INSERT INTO `log` VALUES ('j2ee', '2019-10-27 20:23:37     http://localhost:8080/addItemToCart?workingItemId=EST-6添加商品（EST-6)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-27 20:24:19     http://localhost:8080/addItemToCart?workingItemId=EST-13添加商品（EST-13)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-27 20:29:17     http://localhost:8080/addItemToCart?workingItemId=EST-13添加商品（EST-13)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-28 10:23:58     http://localhost:8080/addItemToCart?workingItemId=EST-18添加商品（EST-18)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-28 10:42:41     http://localhost:8080/viewListOrder?username=j2ee 查看订单 [org.csu.mypetstore.domain.Order@61fe9ec9]');
INSERT INTO `log` VALUES ('j2ee', '2019-10-29 08:17:55     http://localhost:8080/addItemToCart?workingItemId=EST-11添加商品（EST-11)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-29 08:18:45     http://localhost:8080/viewListOrder?username=j2ee 查看订单 [org.csu.mypetstore.domain.Order@66e79e68]');
INSERT INTO `log` VALUES ('j2ee', '2019-10-29 08:24:53     http://localhost:8080/removeItemFromCart?cartItem=EST-11从购物车中移除商品（EST-11)');
INSERT INTO `log` VALUES ('ACID', '2019-10-29 08:26:14     http://localhost:8080/addItemToCart?workingItemId=EST-19添加商品（EST-19)到购物车');
INSERT INTO `log` VALUES ('j2ee', '2019-10-29 08:30:21     http://localhost:8080/viewItem?itemId=EST-6浏览（EST-6)商品');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `orderid` int(11) NOT NULL,
  `userid` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `orderdate` date NOT NULL,
  `shipaddr1` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shipaddr2` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `shipcity` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shipstate` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shipzip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shipcountry` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `billaddr1` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `billaddr2` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `billcity` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `billstate` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `billzip` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `billcountry` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `courier` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `totalprice` decimal(10, 2) NULL DEFAULT NULL,
  `billtofirstname` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `billtolastname` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shiptofirstname` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shiptolastname` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `creditcard` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exprdate` varchar(7) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cardtype` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `locale` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`orderid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (1001, 'j2ee', '2019-10-27', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', 'UPS', 521.50, 'ABC', 'XYX', 'ABC', 'XYX', '999 9999 9999 9999', '12/03', 'Visa', 'CA');
INSERT INTO `orders` VALUES (1003, 'ACID', '2019-10-29', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', 'UPS', 134.60, 'ABC', 'XYX', 'ABC', 'XYX', '999 9999 9999 9999', '12/03', 'Visa', 'CA');
INSERT INTO `orders` VALUES (1013, 'j2ee', '2020-04-06', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', '901 San Antonio Road', 'MS UCUP02-206', 'Palo Alto', 'CA', '94303', 'USA', 'UPS', 888.80, 'ABC', 'XYX', 'ABC', 'XYX', '999999999999', '12/03', 'Visa', 'CA');

-- ----------------------------
-- Table structure for orderstatus
-- ----------------------------
DROP TABLE IF EXISTS `orderstatus`;
CREATE TABLE `orderstatus`  (
  `orderid` int(11) NOT NULL,
  `linenum` int(11) NOT NULL,
  `timestamp` date NOT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`orderid`, `linenum`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderstatus
-- ----------------------------
INSERT INTO `orderstatus` VALUES (1001, 1001, '2019-10-27', '待发货');
INSERT INTO `orderstatus` VALUES (1003, 1003, '2019-10-29', '待发货');
INSERT INTO `orderstatus` VALUES (1013, 1013, '2020-04-06', '待发货');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `productid` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `category` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `text` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`productid`) USING BTREE,
  INDEX `productCat`(`category`) USING BTREE,
  INDEX `productName`(`name`) USING BTREE,
  CONSTRAINT `fk_product_1` FOREIGN KEY (`category`) REFERENCES `category` (`catid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('aaa1', 'FISH', 'Kind Dragon', '../images/lizard1.gif', 'Male Magic DOG');
INSERT INTO `product` VALUES ('AV-CB-01', 'BIRDS', 'Amazon Parrot', '../images/bird2.gif', 'Great companion for up to 75 years');
INSERT INTO `product` VALUES ('AV-SB-02', 'BIRDS', 'Finch', '../images/bird1.gif', 'Great stress reliever');
INSERT INTO `product` VALUES ('bbb', 'BIRDS', 'Wahana', '../images/bird1.gif', 'Good family birds');
INSERT INTO `product` VALUES ('ccc', 'DOGS', 'Dog test1', '../images/dog1.gif', 'Good dog for testing');
INSERT INTO `product` VALUES ('ddd', 'CATS', 'Cat test2', '../images/cat2.gif', 'Great for reducing mouse populations');
INSERT INTO `product` VALUES ('eee', 'BIRDS', 'bird', NULL, 'Great bird for testing');
INSERT INTO `product` VALUES ('FI-FW-01', 'FISH', 'Koi', '../images/fish3.gif', 'Fresh Water fish from Japan');
INSERT INTO `product` VALUES ('FI-FW-02', 'FISH', 'Goldfish', '../images/fish2.gif', 'Fresh Water fish from China');
INSERT INTO `product` VALUES ('FI-SW-01', 'FISH', 'Angelfish', '../images/fish1.gif', 'Salt Water fish from Australia');
INSERT INTO `product` VALUES ('FI-SW-02', 'FISH', 'Tiger Shark', '../images/fish4.gif', 'Salt Water fish from Australia');
INSERT INTO `product` VALUES ('FL-DLH-02', 'CATS', 'Persian', '../images/cat1.gif', 'Friendly house cat, doubles as a princess');
INSERT INTO `product` VALUES ('FL-DSH-01', 'CATS', 'Manx', '../images/cat2.gif', 'Great for reducing mouse populations');
INSERT INTO `product` VALUES ('ggg', 'CATS', 'Cat testg', '../images/cat2.gif', 'Great for reducing mouse populations');
INSERT INTO `product` VALUES ('K9-BD-01', 'DOGS', 'Bulldog', '../images/dog2.gif', 'Friendly dog from England');
INSERT INTO `product` VALUES ('K9-CW-01', 'DOGS', 'Chihuahua', '../images/dog4.gif', 'Great companion dog');
INSERT INTO `product` VALUES ('K9-DL-01', 'DOGS', 'Dalmation', '../images/dog6.gif', 'Great dog for a Fire Station');
INSERT INTO `product` VALUES ('K9-PO-02', 'DOGS', 'Poodle', '../images/dog6.gif', 'Cute dog from France');
INSERT INTO `product` VALUES ('K9-RT-01', 'DOGS', 'Golden Retriever', '../images/dog1.gif', 'Great family dog');
INSERT INTO `product` VALUES ('K9-RT-02', 'DOGS', 'Labrador Retriever', '../images/dog5.gif', 'Great hunting dog');
INSERT INTO `product` VALUES ('RP-LI-02', 'REPTILES', 'Iguana', '../images/lizard1.gif', 'Friendly green friend');
INSERT INTO `product` VALUES ('RP-SN-01', 'REPTILES', 'Rattlesnake', '../images/lizard1.gif', 'Doubles as a watch dog');

-- ----------------------------
-- Table structure for profile
-- ----------------------------
DROP TABLE IF EXISTS `profile`;
CREATE TABLE `profile`  (
  `userid` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `langpref` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `favcategory` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mylistopt` tinyint(1) NULL DEFAULT NULL,
  `banneropt` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`userid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of profile
-- ----------------------------
INSERT INTO `profile` VALUES ('j2ee', 'english', 'FISH', 1, 1);
INSERT INTO `profile` VALUES ('Peak', 'CHINESE', 'DOGS', 1, 1);

-- ----------------------------
-- Table structure for sequence
-- ----------------------------
DROP TABLE IF EXISTS `sequence`;
CREATE TABLE `sequence`  (
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nextid` int(11) NOT NULL,
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence
-- ----------------------------
INSERT INTO `sequence` VALUES ('linenum', 1000);
INSERT INTO `sequence` VALUES ('ordernum', 1014);

-- ----------------------------
-- Table structure for signon
-- ----------------------------
DROP TABLE IF EXISTS `signon`;
CREATE TABLE `signon`  (
  `username` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of signon
-- ----------------------------
INSERT INTO `signon` VALUES ('admin', '$2a$10$3sLEhmvrLjt2cYclHnKWvOCPHsJk9ROxRY7w5smF9wBPiiKrG2nEG');
INSERT INTO `signon` VALUES ('j2ee', '$2a$10$A.fJzDcmuiUI.E2o5pmpuOM189jw/X9R7rIoY6.eAURPYz13N2VgC');
INSERT INTO `signon` VALUES ('Peak', '$2a$10$ftVGj167kK2bpviAX0gX6ONxp0RTpvNjeoHFA1GStvLq0zrPEpJSK');

-- ----------------------------
-- Table structure for supplier
-- ----------------------------
DROP TABLE IF EXISTS `supplier`;
CREATE TABLE `supplier`  (
  `suppid` int(11) NOT NULL,
  `name` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `addr1` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `addr2` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zip` varchar(5) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(80) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`suppid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of supplier
-- ----------------------------
INSERT INTO `supplier` VALUES (1, 'XYZ Pets', 'AC', '600 Avon Way', '', 'Los Angeles', 'CA', '94024', '212-947-0797');
INSERT INTO `supplier` VALUES (2, 'ABC Pets', 'AC', '700 Abalone Way', '', 'San Francisco ', 'CA', '94024', '415-947-0797');

-- ----------------------------
-- Triggers structure for table item
-- ----------------------------
DROP TRIGGER IF EXISTS `quantity_check`;
delimiter ;;
CREATE TRIGGER `quantity_check` AFTER UPDATE ON `item` FOR EACH ROW begin 
    if (new.stockquantity <= 0) then  
        update item set  stockquantity = old.stockquantity where itemid = new.itemid;   
    end if;
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
