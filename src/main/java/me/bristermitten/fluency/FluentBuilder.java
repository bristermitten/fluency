package me.bristermitten.fluency;

import me.bristermitten.fluency.button.ButtonBuilder;
import me.bristermitten.fluency.button.MenuButton;
import me.bristermitten.fluency.menu.MenuBuilder;

/**
 * A fluent builder is a design pattern similar to a builder, with a few differences:
 *
 * 1. A fluent builder holds a reference to the object it is building, and delegates its methods to that object,
 * rather than storing the values and then creating a new object. Because of this, it is not recommended to build an
 * immutable type with a fluent builder. Essentially, a fluent builder is 1 way binding to the object.
 * <p>
 * 2. Given that a fluent builder is already "built", callers should do something with the result immediately.
 * For example, {@link MenuBuilder#buildButton()} immediately adds the button to the menu, and THEN returns the new builder
 * utilising the mutability, allowing changes to be made at any time and all objects using the result
 * of {@link FluentBuilder#build()} are seamlessly updated.
 * <p>
 * 3. For method chaining, fluent builders have a parent builder, which can be null. For example {@link ButtonBuilder}'s
 * parent builder is {@link MenuBuilder}, however {@link MenuBuilder} has no parent. This is because of the hierarchy of
 * items in the API: a Menu has buttons, and each button has a click handler.
 * Instead of returning null if a builder has no parent, the builder should return itself.
 * However, while builders have parents, they do not have to be chained. A {@link MenuButton} can be crafted standalone
 * with {@link Fluency#buildButton()}, however it's important to note that this will have a null parent
 * To identify if a builder has a parent or not, assume it to be null unless the builder was obtained
 * through chaining (eg {@link MenuBuilder#buildButton()}.
 *
 * @param <T> the type that this builder creates
 * @param <P> the parent builder, which will be itself if the builder has no parent
 */
public interface FluentBuilder<T, P extends FluentBuilder<?, ?>> {

    void invalidate();

    T build();

    P done();
}
