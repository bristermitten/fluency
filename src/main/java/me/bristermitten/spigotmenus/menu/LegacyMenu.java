package me.bristermitten.spigotmenus.menu;

class LegacyMenu {
//    private final LinkedList<MenuButton> buttons;
//    private final PageList pages;
//    /**
//     * Local maximum size for pagination. Means a menu can have max n rows and still have pages added
//     */
//    private final int maxSize;
//    Inventory inventory;
//    //MENU INFO
//    private String title;
//    private int size;
//
//    public LegacyMenu(String title, int size, MenuButton... buttons) {
//        Objects.requireNonNull(title, "title");
//        Validate.isTrue(size > 0, "size is negative");
//
//        this.title = title;
//        this.size = size;
//        this.maxSize = size;
//        this.buttons = new FixedCapacityArrayList<>(size, Arrays.asList(buttons));
//        this.pages = new PageList(this);
//        updateInfo();
//    }
//
//    @Override
//    public String toString() {
//        return "LegacyMenu{" +
//                "buttons=" + buttons +
//                ", pages=" + pages +
//                ", maxSize=" + maxSize +
//                ", title='" + title + '\'' +
//                ", size=" + size +
//                '}';
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        LegacyMenu menu = (LegacyMenu) o;
//        return maxSize == menu.maxSize &&
//                size == menu.size &&
//                buttons.equals(menu.buttons) &&
//                title.equals(menu.title) &&
//                pages.equals(menu.pages);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(buttons, pages, maxSize, title, size);
//    }
//
//    protected void updateInfo() {
//        if (size > maxSize) {
//            pages.clear();
//            int pagesAmount = pagesNeeded(size);
//            for (int i = 0; i < pagesAmount; i++) {
//                addPage();
//            }
//            size = maxSize;
//        }
//        this.inventory = Bukkit.createInventory(new MenuHolder(this), size, Chat.color(title));
//
//        for (int i = 0; i < buttons.size(); i++) {
//            MenuButton menuButton = buttons.get(i);
//            ItemStack item = menuButton == null ? null : menuButton.getItem();
//            this.inventory.setItem(i, item);
//        }
//    }
//
//    public Page addPage() {
//        int pageNumber = pages.size();
//        Page page = createNewPage(pageNumber);
//
//        //copy over previous button if it exists
//        LegacyMenu lastPage = pages.getLast();
//        val lastButton = lastPage.buttons.getLast();
//        if (lastButton != null) {
//            page.addButton(lastButton, lastPage.size - 1);
//        }
//
//        //add next page button
//        lastPage.addButton(MenuButtons.NEXT_PAGE, lastPage.size - 1);
//        pages.add(page);
//        return page;
//    }
//
//    private Page createNewPage(int pageNumber) {
//        Page page = new Page(title + " Page " + pageNumber, size, this);
//        page.addButton(MenuButtons.PREVIOUS_PAGE, 0);
//        return page;
//    }
//
//    protected int firstEmpty() {
//        int firstEmpty = inventory.firstEmpty();
//        if (firstEmpty != -1) return firstEmpty;
//        Iterator<LegacyMenu> pageIterator = pages.iterator();
//        pageIterator.next(); //we've already counted the first page
//        while (firstEmpty == -1 && pageIterator.hasNext()) {
//            LegacyMenu page = pageIterator.next();
//            int pageFirstEmpty = page.firstEmpty();
//            if (pageFirstEmpty == -1) {
//                firstEmpty += page.size;
//            } else return firstEmpty + pageFirstEmpty;
//        }
//        if (firstEmpty == -1) {
//            return addPage().firstEmpty();
//        }
//        return firstEmpty;
//    }
//
//    public void addButton(MenuButton button) {
//        addButton(button, firstEmpty());
//    }
//
//
//    public void addButton(MenuButton button, int slot) {
//        LegacyMenu p = pageFor(slot);
//        p.buttons.set(slot, button);
//        updateInfo();
//    }
//
//    private LegacyMenu pageFor(int slot) {
//        if (slot <= maxSize) return this;
//        if (slot >= pages.lastSlot())
//            throw new IndexOutOfBoundsException("LegacyMenu cannot fit slot " + slot + ", max slot: " + pages.lastSlot());
//        AtomicInteger temp = new AtomicInteger(maxSize);
//        AtomicReference<LegacyMenu> page = new AtomicReference<>();
//        pages.pageIterator().forEachRemaining(m -> {
//            temp.addAndGet(m.maxSize);
//            if (temp.get() >= slot) page.set(m);
//        });
//        return page.get();
//    }
//
//    private int pagesNeeded(int size) {
//        if (size >= MAX_INV_SIZE) {
//            return 1;
//        }
//        int pagesAmount = 0;
//        while (size >= MAX_PAGE_SIZE) {
//            size -= MAX_PAGE_SIZE;
//            pagesAmount++;
//        }
//        return pagesAmount;
//    }
//
//    public MenuButton getButton(int slot) {
//        return buttons.get(slot);
//    }
//
//    public void open(Player whoClicked) {
//        whoClicked.openInventory(inventory);
//    }
//
//    public LinkedList<LegacyMenu> getPages() {
//        return pages;
//    }
//
//    public LegacyMenu setTitle(String newTitle) {
//        this.title = newTitle;
//        updateInfo();
//        return this;
//    }
//
//    /**
//     * Get only the buttons in this page/menu
//     *
//     * @return
//     */
//    public LinkedList<MenuButton> getButtons() {
//        return this.buttons;
//    }
//
//    public LinkedList<MenuButton> getAllButtons() {
//        LinkedList<MenuButton> menuButtons = new LinkedList<>();
//        for (LegacyMenu m : this.pages) {
//            menuButtons.addAll(m.getButtons());
//        }
//        return menuButtons;
//    }
//
//    public int getSize() {
//        return size;
//    }
//
//    public LegacyMenu setSize(int newSize) {
//        this.size = newSize;
//        updateInfo();
//        return this;
//    }
}
