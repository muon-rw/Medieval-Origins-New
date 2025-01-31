## 7.0.0-alpha-7
- Cleaned up the description of Revenant's Revenance power, split into badges
- Revenants now restore a small amount of hunger when eating bones or heads
- Fixed bones and heads not being edible if at full hunger
- Removed reference to nonexistent Instant Cure power
- Clarify spell engine dependency in fabric.mod.json, restrict from upcoming version

## 7.0.0-alpha-6
- Fix Incubus' Unholy Deal not dealing any damage to the player

## 7.0.0-alpha-5
- Add FTB Teams support to Valkyrie intervention/divine smite targeting logic
- Add FTB Teams support to Revenant summons targeting
- Fixed the badge tip for Pixie's flight effects toggle showing the wrong key
- Alfiq can now charge Pounce while moving
- Alfiq meows are now louder and more spammable :)
- Alfiq player pickpocketing now has a cooldown
- Made Goblins significantly less green, and Moon Elves a little less blue
- Revenant Black Thumb should now work on most modded crop blocks automatically
- Fixed the pickpocket cooldown bar always showing

## 7.0.0-alpha-4
- Compatible with Origins alpha.12
- Wood Elf covered-stealth is now a bit more "stealthy" (since it's now harder to apply)

## 7.0.0-alpha-3
- Fix untranslated usability hints in tooltips
- Fix Valkyrie Intervention heal not scaling based off of healing spell power
- Intervention now pushes back non-allies when applying
- Fix Revenant's soul spell power based summon scaling

## 7.0.0-alpha-2
- Fix Moon/Wood Elf stealths not affecting mobs
- Fix Wood Elf stealth being triggerable by crouching in between short crops 
- Fix Faes not having increased mob allure
- Fix Banshees being targeted by mobs during Spectral
- Fix Pixie particles not rendering
- Fix Valkyrie's Intervention ability not being usable
- Intervention will now always end after 10 seconds if not used.

## 7.0.0-alpha-1
*Initial alpha port to 1.21.1*


**There will be bugs.**
**Back up your worlds before installing, and frequently while playing!**

- No longer bundles Apugli or requires Pehkui - performance should be significantly better!
- Now Fabric only - when Origins Fabric is out of alpha and Sinytra Connector updates, I'll contribute to improving their compatibility wherever possible
- Valkyries now get extra Vanquisher points for slaying undead bosses
- High Elf's Glacial Step now increases level of Frost Walker
- Added a keybind "Tertiary Active" for pixie effects and the new revenant command power.
- "Golden Armor" now also includes gold-trimmed armor
- Revenants can now command their summons to "sit", or teleport all owned summons to them
- Revenants can now eat bones/heads even at full hunger
- Revenants are now limited to 5 summons of any type at a time
- Revenant summon kills now grant EXP and kill credit
- Revenant summons will now always drop any items you give them
- Revenant skeletons will now no longer be targeted by tamed wolves
- Revenant's combat summons now expire (5 minutes for zombies, 10 for skeletons, 30 for wither skeletons), added a duration bar 
- Putrid Communion now increases the *duration* of combat summons
- *Attributes* of combat summons are now only determined by Soul Spell Power
- Fixed some death messages being untranslated
- Fixed a crash when a Valkyrie cleared multiple negative effects from another player at once
- Fixed spell damage powers crashing on dedicated servers in rare mod compatibility cases 