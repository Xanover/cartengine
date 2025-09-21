# cartengine

This mod modifies [Audaki Cart Engine](https://github.com/audaki/minecraft-cart-engine) to fix incompatibilities with certain minecart-based redstone contraptions like minecart unloaders, as described in [Issue #58](https://github.com/audaki/minecraft-cart-engine/issues/58) of ACE. It accomplishes this by only modifying minecart behaviour of normal minecarts, while excluding non-ridable ones. 

If applying the old logic on a ridable cart is desired, said cart can simply be renamed to `NotModified` in an anvil.
