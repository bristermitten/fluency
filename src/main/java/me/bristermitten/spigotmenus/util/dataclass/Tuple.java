package me.bristermitten.spigotmenus.util.dataclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Tuple<A, B> {
    private A a;
    private B b;
}
