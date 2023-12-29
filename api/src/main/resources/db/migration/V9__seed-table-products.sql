-- Burgers
INSERT INTO products (name, ingredients, price, category, active) VALUES
('Hambúrguer Clássico', 'Pão, carne, queijo, alface, tomate, molho especial', 15.99, 'BURGER', true),
('Hambúrguer Vegetariano', 'Pão integral, hambúrguer de quinoa, queijo, alface, tomate, maionese vegana', 14.50, 'BURGER', true),
('Hambúrguer Duplo', 'Pão, dois hambúrgueres, bacon, queijo, cebola caramelizada, molho barbecue', 18.75, 'BURGER', true),
('X-Bacon', 'Pão, hambúrguer, bacon, queijo, alface, tomate, maionese', 17.25, 'BURGER', true),
('Hambúrguer de Frango', 'Pão, hambúrguer de frango, queijo, alface, tomate, molho especial', 16.50, 'BURGER', true),
('Hambúrguer Vegano de Feijão', 'Pão vegano, hambúrguer de feijão, tomate, alface, cebola roxa, maionese vegana', 15.75, 'BURGER', true),
('X-Tudo', 'Pão, hambúrguer, presunto, ovo, bacon, queijo, alface, tomate, maionese', 19.99, 'BURGER', true),
('Hambúrguer de Picanha', 'Pão, hambúrguer de picanha, queijo, rúcula, tomate seco, molho especial', 20.25, 'BURGER', true),
('Hambúrguer de Salmão', 'Pão integral, hambúrguer de salmão, cream cheese, alface, cebola roxa, molho de ervas', 22.00, 'BURGER', true),
('Hambúrguer de Cordeiro', 'Pão, hambúrguer de cordeiro, queijo de cabra, rúcula, cebola caramelizada, molho de hortelã', 21.50, 'BURGER', true);

-- Drink
INSERT INTO products (name, ingredients, price, category, active) VALUES
('Refrigerante Coca-Cola', 'Coca-Cola, gelo', 5.99, 'DRINK', true),
('Suco Natural de Laranja', 'Laranja, água, açúcar', 7.50, 'DRINK', true),
('Água Mineral sem Gás', 'Água mineral', 3.75, 'DRINK', true),
('Café Expresso', 'Café, água quente', 4.25, 'DRINK', true),
('Chá Gelado de Pêssego', 'Chá de pêssego, gelo, açúcar', 6.25, 'DRINK', true),
('Milkshake de Chocolate', 'Leite, sorvete de chocolate, chantilly', 8.99, 'DRINK', true),
('Coquetel de Frutas', 'Frutas variadas, suco de laranja, gelo', 9.50, 'DRINK', true),
('Vinho Tinto Seco', 'Vinho tinto, taça', 15.25, 'DRINK', true),
('Cerveja Artesanal IPA', 'Cerveja IPA, copo', 10.75, 'DRINK', true),
('Limonada Suíça', 'Limão, água, açúcar, gelo', 6.75, 'DRINK', true);

-- Entry
INSERT INTO products (name, ingredients, price, category, active) VALUES
('Bruschetta de Tomate e Manjericão', 'Pão italiano, tomate, manjericão, azeite de oliva, alho', 12.99, 'ENTRY', true),
('Carpaccio de Carne', 'Carne finamente fatiada, molho mostarda, queijo parmesão, rúcula', 14.50, 'ENTRY', true),
('Ceviche de Peixe Branco', 'Peixe branco, limão, cebola roxa, coentro, pimenta, milho', 13.75, 'ENTRY', true),
('Rolinhos Primavera', 'Massa de arroz, legumes, molho agridoce', 11.25, 'ENTRY', true),
('Dadinhos de Tapioca', 'Tapioca, queijo coalho, geleia de pimenta', 10.50, 'ENTRY', true),
('Caldo Verde', 'Batata, couve, linguiça defumada, azeite de oliva', 9.99, 'ENTRY', true),
('Pastel de Queijo', 'Massa de pastel, queijo, temperos', 8.50, 'ENTRY', true),
('Salada Caprese', 'Tomate, mussarela de búfala, manjericão, azeite de oliva', 11.75, 'ENTRY', true),
('Pão de Alho', 'Pão francês, alho, manteiga, temperos', 10.25, 'ENTRY', true),
('Tábua de Frios', 'Diversos tipos de queijo e presunto, azeitonas, torradas', 15.75, 'ENTRY', true);

-- Dessert
INSERT INTO products (name, ingredients, price, category, active) VALUES
('Pudim de Leite', 'Leite condensado, leite, ovos, açúcar', 9.99, 'DESSERT', true),
('Torta de Chocolate', 'Bolacha maisena, chocolate, creme de leite, manteiga', 12.50, 'DESSERT', true),
('Mousse de Maracujá', 'Maracujá, creme de leite, leite condensado', 10.75, 'DESSERT', true),
('Cheesecake de Morango', 'Cream cheese, morangos, biscoito, açúcar', 11.25, 'DESSERT', true),
('Sorvete de Creme', 'Leite, creme de leite, açúcar, essência de baunilha', 8.50, 'DESSERT', true),
('Torta de Limão', 'Bolacha maisena, leite condensado, limão', 10.99, 'DESSERT', true),
('Brigadeiro', 'Leite condensado, chocolate em pó, manteiga', 7.75, 'DESSERT', true),
('Creme Brûlée', 'Creme de leite, gemas, açúcar, baunilha', 12.25, 'DESSERT', true),
('Tiramisu', 'Biscoito champanhe, café, queijo mascarpone, cacau em pó', 13.75, 'DESSERT', true),
('Pavê de Chocolate e Morango', 'Bolacha maisena, chocolate, morango, creme de leite', 11.50, 'DESSERT', true);

-- Side dishes
INSERT INTO products (name, ingredients, price, category, active) VALUES
('Arroz Branco', 'Arroz, água, sal', 5.99, 'SIDE_DISHES', true),
('Feijão Tropeiro', 'Feijão, farinha de mandioca, bacon, linguiça, ovos, temperos', 8.50, 'SIDE_DISHES', true),
('Purê de Batata', 'Batata, leite, manteiga, sal', 6.75, 'SIDE_DISHES', true),
('Farofa de Alho', 'Farinha de mandioca, alho, manteiga, temperos', 7.25, 'SIDE_DISHES', true),
('Legumes Grelhados', 'Abobrinha, berinjela, pimentão, temperos', 9.50, 'SIDE_DISHES', true),
('Salada Mista', 'Alface, tomate, pepino, cebola, temperos, vinagrete', 7.99, 'SIDE_DISHES', true),
('Batata Gratinada', 'Batata, creme de leite, queijo, temperos', 8.25, 'SIDE_DISHES', true),
('Brócolis ao Alho e Óleo', 'Brócolis, alho, azeite de oliva, sal', 6.99, 'SIDE_DISHES', true),
('Legumes Cozidos', 'Cenoura, brócolis, vagem, temperos', 7.75, 'SIDE_DISHES', true),
('Quiabo Refogado', 'Quiabo, cebola, tomate, temperos', 8.50, 'SIDE_DISHES', true),
('Batata Frita Simples', 'Batatas, sal', 8.99, 'SIDE_DISHES', true),
('Batata Frita com Cheddar e Bacon', 'Batatas, queijo cheddar, bacon, cebolinha, maionese', 12.50, 'SIDE_DISHES', true),
('Batata Frita com Molho Especial', 'Batatas, molho especial, temperos', 10.75, 'SIDE_DISHES', true),
('Batata Frita com Parmesão e Ervas', 'Batatas, queijo parmesão, ervas finas, azeite de oliva, sal', 11.25, 'SIDE_DISHES', true),
('Batata Frita com Maionese de Alho', 'Batatas, maionese de alho, temperos', 10.50, 'SIDE_DISHES', true),
('Batata Frita com Molho Barbecue', 'Batatas, molho barbecue, temperos', 11.99, 'SIDE_DISHES', true),
('Batata Frita com Pimenta e Limão', 'Batatas, pimenta, limão, sal', 10.25, 'SIDE_DISHES', true),
('Batata Frita com Cebola Crocante', 'Batatas, cebola crocante, temperos especiais', 11.50, 'SIDE_DISHES', true),
('Batata Frita com Alecrim e Alho', 'Batatas, alecrim, alho, azeite de oliva, sal', 10.75, 'SIDE_DISHES', true),
('Batata Frita com Molho Picante', 'Batatas, molho picante, temperos', 11.75, 'SIDE_DISHES', true);