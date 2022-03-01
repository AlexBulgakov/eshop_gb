INSERT INTO `users` (`username`, `password`)
    VALUE   ('admin', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq'),
        ('guest', '$2a$10$uedJ6jkBS08x5mxZY6gV6.LAKSd202CiVutxz5VDq3TIyj9alkmIq');
GO

INSERT INTO `roles` (`name`)
VALUE ('ROLE_ADMIN'), ('ROLE_GUEST');
GO

INSERT INTO `users_roles`(`user_id`, `role_id`)
SELECT (SELECT id FROM `users` WHERE `username` = 'admin'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_ADMIN')
UNION ALL
SELECT (SELECT id FROM `users` WHERE `username` = 'guest'), (SELECT id FROM `roles` WHERE `name` = 'ROLE_GUEST');

GO

INSERT INTO `categories` (`name`)
VALUE ('Notebook')

GO

INSERT INTO `products` (`name`, `price`)
VALUE ('Acer'), ('90_000')

GO

INSERT INTO `orders` (`count`)
VALUE ('00001')

GO

INSERT INTO `order_products`(`order_id`, `product_id`)
SELECT (SELECT id FROM `orders` WHERE `count` = '00001'), (SELECT id FROM `products` WHERE `name` = 'Acer')


GO