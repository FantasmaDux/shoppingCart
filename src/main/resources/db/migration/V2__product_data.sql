INSERT INTO categories (name) VALUES
  ('Электроника'),
  ('Одежда'),
  ('Книги')
ON CONFLICT (id) DO NOTHING;

INSERT INTO products (name, brand, price, inventory, description, category_id)
SELECT
    p_data.name,
    p_data.brand,
    p_data.price,
    p_data.inventory,
    p_data.description,
    c.id
FROM (VALUES
          -- Электроника
          ('iPhone 15 Pro', 'Apple', 129999, 25, 'Флагманский смартфон Apple', 'Электроника'),
          ('Galaxy S23 Ultra', 'Samsung', 109999, 30, 'Смартфон с S-Pen', 'Электроника'),
          ('MacBook Air M2', 'Apple', 119999, 15, 'Ультратонкий ноутбук', 'Электроника'),
          ('PlayStation 5', 'Sony', 59999, 40, 'Игровая консоль', 'Электроника'),

          -- Одежда
          ('Джинсы Levi''s 501', 'Levi''s', 7999, 60, 'Классические джинсы', 'Одежда'),
          ('Куртка зимняя', 'The North Face', 24999, 45, 'Теплая пуховая куртка', 'Одежда'),
          ('Футболка Nike', 'Nike', 1999, 200, 'Хлопковая футболка', 'Одежда'),

          -- Книги
          ('Мастер и Маргарита', 'АСТ', 599, 150, 'Роман Булгакова', 'Книги'),
          ('1984', 'Эксмо', 499, 200, 'Антиутопия Оруэлла', 'Книги'),
          ('Гарри Поттер', 'Махаон', 899, 100, 'Книга о юном волшебнике', 'Книги')
     ) AS p_data(name, brand, price, inventory, description, category_name)
         JOIN categories c ON c.name = p_data.category_name
ON CONFLICT DO NOTHING;