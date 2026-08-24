You are running the implementation phase for one Openclaw Android app.

Use these orchestrator instructions as binding context: /home/codex-agent/codex-app-agent/AGENTS.md
Use this screen spec: /home/codex-agent/codex-app-agent/screens-shop.md
Project directory: /tmp/gul-cart

Task metadata:
- Asana task gid: 1217580748339427
- Asana task name: GB GW4 C1506
- Asana URL: https://app.asana.com/1/1208304498069546/project/1213586227413017/task/1217580748339427
- App name: Gul Cart
- Company: GUL MART LTD
- Domain: https://gulmart.casa
- Package: gulmartcorp.grocerystore.gulcart
- Prefix: AHSQY
- Type: shop
- Description: Специфика компании — торговля продуктами питания, напитками и товарами повседневного спроса. Приложение по продаже товаров компании, содержит список всех продуктов, напитков, бакалеи и готовых товаров (с возможностью сортировки по категориям). История покупок. Корзина товаров с формой оформления заказа. После подтверждения заказа пользователь видит баннер с информацией о номере и деталях заказа с уведомлением о сроках доставки или самовывоза. Настройки должны содержать информацию о: названии компании, версии приложения, ссылке Customers Support на сайт компании.  
(можно разнообразить главную страницу каруселью с акциями, рецептами или статьями о продуктах и питании)

Do Phase 2 and Phase 3 only:
1. Extract or derive the style guide.
2. Do not create project-local agent instruction files inside /tmp/gul-cart.
3. Implement all required screens/content/data/assets/icon according to the orchestrator AGENTS.md and the screen spec.
4. Icon generation is best-effort: if Leonardo/imagegen cannot provide a filesystem-backed icon quickly, continue implementing the app with existing assets.
5. Do not push to GitHub, do not update Asana, and do not send Slack.
6. You may run local checks while implementing, but the runner will run quality/build afterward.
7. Keep every Kotlin file conventionally formatted: one statement per line, annotations above declarations, expanded indented Compose blocks, no semicolon-compressed code, and no source line longer than 200 characters.
