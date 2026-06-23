import { useState, useRef, useEffect } from "react";

const FULL_DURATIONS=[300,600,1200,3600,6000];
const EFFECTS=[
  {id:"MobEffects.DAMAGE_BOOST",label:"Strength",durations:FULL_DURATIONS},
  {id:"MobEffects.DIG_SPEED",label:"Haste",durations:FULL_DURATIONS},
  {id:"MobEffects.FIRE_RESISTANCE",label:"Fire Resistance",durations:FULL_DURATIONS},
  {id:"MobEffects.GLOWING",label:"Glowing",durations:FULL_DURATIONS},
  {id:"MobEffects.LUCK",label:"Luck",durations:FULL_DURATIONS},
  {id:"MobEffects.MOVEMENT_SPEED",label:"Speed",durations:FULL_DURATIONS},
  {id:"MobEffects.NIGHT_VISION",label:"Night Vision",durations:FULL_DURATIONS},
  {id:"MobEffects.REGENERATION",label:"Regeneration",durations:FULL_DURATIONS},
  {id:"MobEffects.SLOW_FALLING",label:"Slow Falling",durations:FULL_DURATIONS},
  {id:"MobEffects.WATER_BREATHING",label:"Water Breathing",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.ANIMAL_CHARM",label:"Animal Charm",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.BALANCED",label:"Balanced",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.BONDING",label:"Bonding",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.CHARISMA",label:"Charisma",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.COMBUSTION",label:"Combustion",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.COMFORT",label:"Comfort",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.EXPLOSION",label:"Explosion",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.FARMERS_BLESSING",label:"Farmer's Blessing",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.FEAST",label:"Feast",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.FLIGHT",label:"Flight",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.FORTUNE",label:"Fortune",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.GRANDMAS_BLESSING",label:"Grandma's Blessing",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.LIFE_LEECH",label:"Life Leech",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.LIGHTNING",label:"Lightning",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.MINING",label:"Mining",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.MUSTARD",label:"Mustard",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.NOURISHMENT",label:"Nourishment",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.PACIFY",label:"Pacify",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.PARTY_STARTER",label:"Party Starter",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.PRESERVATION",label:"Preservation",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.REPULSION",label:"Repulsion",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.RESTED",label:"Rested",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.SATIATED_SHIELD",label:"Satiated Shield",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.SATIATION",label:"Satiation",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.SUGAR_RUSH",label:"Sugar Rush",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.REST",label:"Rest",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.SUSTENANCE",label:"Sustenance",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.TOUCH_ABSORB",label:"Touch Absorb",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.TOUCH_HEAL",label:"Touch Heal",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.TOUCH_POISON",label:"Touch Poison",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.TOUCH_REGEN",label:"Touch Regen",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.TOUGH",label:"Tough",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.TUNDRA_STRIDER",label:"Tundra Strider",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.VIGOR",label:"Vigor",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.VITALITY",label:"Vitality",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.WARMTH",label:"Warmth",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.STOUT_HEART",label:"Stout Heart",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.RAGING",label:"Raging",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.SWEET_HEART",label:"Sweet Heart",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.REFRESHED",label:"Refreshed",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.WELL_SERVED",label:"Well Served",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.STIMULATION",label:"Stimulation",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.ADRENALINE",label:"Adrenaline",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.BERSERK",label:"Berserk",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.BLOOD_CLOT",label:"Blood Clot",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.BRIMSTONE_VISION",label:"Brimstone Vision",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.CAFFEINATED",label:"Caffeinated",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.LAVA_WALKING",label:"Lava Walking",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.PERCEPTION",label:"Perception",durations:FULL_DURATIONS},
  {id:"ModEffectCategories.WATER_WALKING",label:"Water Walking",durations:FULL_DURATIONS},
];
export const BASE_TOOLTIP_KEYS=[
  "apple","apple_cream_frosting","apple_ice_cream","apple_jam","avocado",
  "bacon","beef","beef_meatballs","beetroot","berry","berry_cream_frosting","berry_ice_cream","berry_jam",
  "brown_mushroom","butter","butterscotch","butterscotch_chips","cacao_mass","caramel","caramel_chips",
  "carrot","cheese","chicken","chocolate","chocolate_chips","chocolate_cream_frosting","chocolate_ice_cream",
  "chocolate_graham_cracker_pie_crust","chorus_fruit","chorus_fruit_cream_frosting","chorus_fruit_ice_cream",
  "chorus_fruit_jam","corn","cream_frosting","crimson_fungus","cucumber",
  "dark_chocolate","dark_chocolate_chips","egg","egg_yolk","eggplant","endermite_meatballs",
  "fish","flesh","fried_egg","ginger","glow_berry","glow_berry_cream_frosting","glow_berry_ice_cream",
  "glow_berry_jam","graham_cracker_pie_crust","green_tea","hash_browns","honey","ice_cream",
  "kelp","lettuce","marshmallow","melon_cream_frosting","melon_ice_cream","melon_jam","mushroom",
  "mutton","onion","peanut_butter","pork","pork_meatballs","potato","pressed_cocoa",
  "rabbit","rabbit_meatballs","red_mushroom","rice","salt","sausage","scrambled_egg",
  "slime","slimeballs","soul_berry","sour_cream","spider_eye","squid_ink","strider_meatballs","sugar",
  "taco_sauce","toast","toffee","toffee_chips","tomato","tomato_sauce",
  "ube_cream_frosting","warped_fungus","white_chocolate","white_chocolate_chips",
];


const CREATE_REC_IDS=new Set(["create:compacting_heated","create:compacting","create:deploying","create:emptying","create:filling","create:item_application","create:milling","create:mixing_heated","create:mixing","create:pressing"]);
const RECIPE_TYPES=[
  {id:"create:compacting_heated",label:"Create — Compacting (heated)",dir:"create/compacting",suffix:"_from_compacting_heated",desc:"Mechanical press with heat. Use for cookies, meatballs, taco shells, pita — anything pressed or shaped with heat."},
  {id:"create:compacting",label:"Create — Compacting",dir:"create/compacting",suffix:"_from_compacting",desc:"Mechanical press, no heat. Use for candy bars, raw shapes, or compacting a fluid into a solid."},
  {id:"create:deploying",label:"Create — Deploying",dir:"create/deploying",suffix:"_from_deploying",desc:"Mechanical deployer applies an item to another. Use for assembling sandwiches, adding toppings, or closing a bun."},
  {id:"create:emptying",label:"Create — Emptying",dir:"create/emptying",suffix:"_from_emptying",desc:"Extracts fluid from a container item. Produces the container and fluid separately."},
  {id:"create:filling",label:"Create — Filling",dir:"create/filling",suffix:"_from_filling",desc:"Fills a container with a fluid. Use to produce bottles, buckets, or bowls from a fluid source."},
  {id:"create:milling",label:"Create — Milling",dir:"create/milling",suffix:"_from_milling",desc:"Millstone grinds a solid into powder or crumbs. Use for grinding sugar, crackers, or dried ingredients."},
  {id:"create:mixing_heated",label:"Create — Mixing (heated)",dir:"create/mixing",suffix:"_from_mixing_heated",desc:"Mechanical mixer with heat. Use for cooked batters, melted chocolate, or anything that needs heat to combine."},
  {id:"create:mixing",label:"Create — Mixing",dir:"create/mixing",suffix:"_from_mixing",desc:"Mechanical mixer, no heat. Use for doughs, cold batters, custards, or blending fluids with solids."},
  {id:"create:item_application",label:"Create — Item Application",dir:"create/item_application",suffix:"_from_item_application",desc:"Deployer applies an item onto a stationary target, returning the result and optionally the applicator."},
  {id:"create:pressing",label:"Create — Pressing",dir:"create/pressing",suffix:"_from_pressing",desc:"Mechanical press on a single item. Use for dicing, slicing, or pressing into chips — one input, one output."},
  {id:"farmersdelight:cooking",label:"Farmer's Delight — Cooking Pot",dir:"farmersdelight/cooking",suffix:"_from_cooking",desc:"Cooking pot recipe combining multiple ingredients over a heat source. The primary recipe type for cooked meals."},
  {id:"farmersdelight:cutting",label:"Farmer's Delight — Cutting",dir:"farmersdelight/cutting",suffix:"_from_cutting",desc:"Cutting board with a knife. Use for portioning block foods into slices, or breaking down ingredients."},
  {id:"minecraft:campfire_cooking",label:"Minecraft — Campfire Cooking",dir:"minecraft/campfire_cooking",suffix:"_from_campfire_cooking",desc:"Single item cooked over a campfire. Slower than smoking. Use as an alternative cooking method."},
  {id:"minecraft:crafting_shaped",label:"Minecraft — Crafting (shaped)",dir:"minecraft/crafting",suffix:"_from_shaped",desc:"Crafting table recipe with a fixed arrangement. Use when the layout of ingredients matters."},
  {id:"minecraft:crafting_shapeless",label:"Minecraft — Crafting (shapeless)",dir:"minecraft/crafting",suffix:"_from_crafting",desc:"Crafting table recipe with no fixed arrangement. Use for simple dry combinations with no processing."},
  {id:"minecraft:smelting",label:"Minecraft — Smelting",dir:"minecraft/smelting",suffix:"_from_smelting",desc:"Furnace recipe for a single item. Use for roasting or cooking a raw ingredient into a finished one."},
  {id:"minecraft:smoking",label:"Minecraft — Smoking",dir:"minecraft/smoking",suffix:"_from_smoking",desc:"Smoker recipe — twice as fast as smelting. Preferred over smelting for food items."},
];
const ITEM_TYPE_OPTIONS=[
  {v:"food",              l:"food",                 hasFood:true,  noDisplay:false, desc:"Standard food item. Stack 64. Shows effects in tooltip. Use for most solid foods."},
  {v:"bowlFood",          l:"bowlFood",             hasFood:true,  noDisplay:true,  desc:"Food item that returns a bowl on use. Stack 16. Shows effects in tooltip. Use for soups and stews. Enable 'Craft remainder returns bowl' if the bowl should also be returned when used as a crafting ingredient."},
  {v:"bottle",            l:"bottle",               hasFood:true,  noDisplay:true,  desc:"DrinkableItem that returns a glass bottle. Stack 16. Use for drinkable fluids."},
  {v:"stickFood",         l:"stickFood",            hasFood:true,  noDisplay:false, desc:"Food item held on a stick. Shows effects in tooltip."},
  {v:"stickFoodCr",       l:"stickFoodCr",          hasFood:true,  noDisplay:false, desc:"Food item held on a stick that also returns the stick as a crafting remainder."},
  {v:"plain",             l:"plain",                hasFood:false, noDisplay:false, desc:"Non-food item with no special properties. Use for ingredients and crafting components."},
  {v:"plainCr",           l:"plainCr",              hasFood:false, noDisplay:false, desc:"Non-food item with a crafting remainder. Choose the remainder below."},
  {v:"ingredientBowl",    l:"ingredientBowl",       hasFood:false, noDisplay:true,  desc:"Non-food ingredient item. Stack 16, returns a bowl. Use for bowl-contained ingredients."},
  {v:"ingredientBottle",  l:"ingredientBottle",     hasFood:false, noDisplay:true,  desc:"Non-drinkable ingredient bottle. Stack 16, returns a glass bottle. Use for bottle-form ingredients that aren't drinkable"},
  {v:"pipingBag",         l:"pipingBag",            hasFood:false, noDisplay:false, desc:"Non-food item. Stack 2, returns a piping bag. Use for frosting and filling piping bags."},
];
function itemTypeInfo(t){return ITEM_TYPE_OPTIONS.find(o=>o.v===t)||ITEM_TYPE_OPTIONS[0];}
function itemTypeHasFood(t){return itemTypeInfo(t).hasFood;}
function itemTypeNoDisplay(t){return itemTypeInfo(t).noDisplay;}
const CRAFT_REMAIN=[{v:"none",l:"— none —"},{v:"cloth_filter",l:"Cloth Filter"},{v:"minecraft:glass_bottle",l:"Glass Bottle"},{v:"piping_bag",l:"Piping Bag"},{v:"minecraft:stick",l:"Stick"}];
const ITEM_REG="common/src/main/java/dev/averageanime/registry/ItemRegistry.java";
const BLOCK_REG="common/src/main/java/dev/averageanime/registry/BlockRegistry.java";
const FLUID_REG="common/src/main/java/dev/averageanime/registry/FluidRegistry.java";
const DISP_REG="common/src/main/java/dev/averageanime/registry/DisplayBlockRegistry.java";
const MOD_CFG="common/src/main/java/dev/averageanime/config/ConfigDefaults.java";
const BUILTIN_COMPAT_KEYS=["tooltip.compat.peanut_butter","tooltip.compat.cinnamon","tooltip.compat.coffee","tooltip.compat.corn","tooltip.compat.eggplant","tooltip.compat.dragon_meat","tooltip.compat.endermite_meat","tooltip.compat.strider_meat","tooltip.compat.raw_flesh_cookie","tooltip.compat.ube","tooltip.compat.raw_ginger_cookie"];
const DISPLAY_TYPES=["PLATE","SMALL_PLATE","BOTTLE","BOWL","SALAD_BOWL","PLATE_FOOD"];
const DISPLAY_AUTO_PATTERNS=["pizza","slice","pie","sandwich","burger","taco","burrito","wrap","cookie","cake","waffle","donut","muffin","pastry","_bowl","_bottle","salad","toast","calzone","smore","cupcake","bar_of","breakfast_bar","gyro","cheese_block"];
function isAutoDisplayBlock(id){if(!id)return false;return DISPLAY_AUTO_PATTERNS.some(p=>id.includes(p));}

export function getNutritionTier(n){if(n<=3)return{tier:1,label:"Minimal",satLo:0.1,satHi:1.9};if(n<=6)return{tier:2,label:"Light",satLo:0.2,satHi:1.6};if(n<=9)return{tier:3,label:"Moderate",satLo:0.2,satHi:1.6};if(n<=12)return{tier:4,label:"Substantial",satLo:0.4,satHi:1.1};return{tier:5,label:"Premium",satLo:0.4,satHi:1.1};}
function parseNs(rawId){const c=rawId&&rawId.includes(":")?rawId.split(":")[0]:"createfood";const localId=rawId&&rawId.includes(":")?rawId.split(":")[1]:rawId;return{ns:c,localId:localId||rawId||"",isCF:c==="createfood"||!rawId};}

function genSimpleBS(id,ns="createfood"){return{variants:{"":{"model":ns+":block/"+id}}};}
function genSimpleLootTable(id,ns="createfood"){return{type:"minecraft:block",pools:[{bonus_rolls:0,conditions:[{condition:"minecraft:survives_explosion"}],entries:[{type:"minecraft:item",name:ns+":"+id}],rolls:1}]};}
function genBlockCuttingRecipe(id,sliceId,sliceCount,ns="createfood"){return{type:"farmersdelight:cutting",ingredients:[{tag:"c:"+id}],tool:{tag:"c:tools/knife"},result:[{item:{count:sliceCount,id:ns+":"+sliceId}}],"neoforge:conditions":[{type:"neoforge:mod_loaded",modid:"farmersdelight"},{type:"createfood:enabled",id:sliceId}],"fabric:load_conditions":[{condition:"fabric:all_mods_loaded",values:["farmersdelight"]},{condition:"createfood:enabled",id:sliceId}]};}
function genSlicesCombineRecipe(id,sliceId,sliceCount,ns="createfood"){return{type:"minecraft:crafting_shapeless",ingredients:Array.from({length:sliceCount},()=>({tag:"c:"+sliceId})),result:{id:ns+":"+id},"neoforge:conditions":[{type:"createfood:enabled",id}],"fabric:load_conditions":[{condition:"createfood:enabled",id}]};}

function buildDisplayBlockRegistration(id,displayConfigs){
  if(!id||!displayConfigs||!displayConfigs.length)return"";
  function configArg(c){
    if(c.maxStack===1&&c.height===12&&!c.hasParticles)return"new DisplayBlockConfig(DisplayType."+c.type+")";
    if(Number.isInteger(c.maxStack)&&c.maxStack!==1&&!c.hasParticles&&c.height===12)return"new DisplayBlockConfig(DisplayType."+c.type+", "+c.maxStack+")";
    if(c.hasParticles)return"new DisplayBlockConfig(DisplayType."+c.type+", "+c.height+", true, () -> ParticleTypes.WHITE_SMOKE)";
    return"new DisplayBlockConfig(DisplayType."+c.type+", "+c.height+")";
  }
  if(displayConfigs.length===1)return"put(m, \""+id+"\", "+configArg(displayConfigs[0])+");";
  const args=displayConfigs.map(configArg).join(",\n        ");
  return"putMulti(m, \""+id+"\",\n        "+args+"\n);";
}
function buildDisplayBlockNote(ids){const list=(Array.isArray(ids)?ids:[ids]).map(i=>"    \""+i+"\"").join(",\n");return "private static final Set<String> EXCLUDED_ITEMS = Set.of(\n"+list+"\n);";}
function mergeDisplayBlockNote(existing,newId){
  const match=existing&&existing.match(/Set\.of\(([\s\S]*?)\)/);
  if(match){
    const existing_ids=match[1].split(",").map(s=>s.trim().replace(/^"|"$/g,"")).filter(Boolean);
    if(!existing_ids.includes(newId))existing_ids.push(newId);
    return buildDisplayBlockNote(existing_ids);
  }
  return buildDisplayBlockNote([newId]);
}
function buildItemJava(item){
  const{id,itemType,nutrition,saturation,fast,crBowl,craftRemainder,effects,tooltipKeys,isCompat,compatKey}=item;
  if(!id)return"";
  const tipArg=(()=>{const keys=(tooltipKeys||[]).map(k=>'"'+k+'_ingredient"').join(", ");const compatSuffix=isCompat&&compatKey?compatKey.replace("tooltip.compat.",""):null;const compat=compatSuffix?'"'+compatSuffix+'"':"null";if((tooltipKeys||[]).length>0||isCompat)return"tips("+compat+(keys?", "+keys:"")+")";return null;})();
  const fxArgs=(effects||[]).map(e=>{const eid=e.effectId.startsWith("ModEffectCategories.")?e.effectId.slice(20):e.effectId;const hasChance=(e.chance??1.0)<1.0;const hasAmp=e.amplifier>0;return"fx("+eid+", "+e.duration+(hasAmp?", "+e.amplifier:hasChance?", 0":"")+(hasChance?", "+(e.chance??1.0).toFixed(2)+"f":"")+")";});
  const t=(tipArg||fxArgs.length)?", "+[tipArg,...fxArgs].filter(Boolean).join(", "):"";
  const nut=nutrition,sat=saturation+"f";
  const idU=id.toUpperCase();
  const itype=itemType||"food";
  if(itype==="plain")           return"public static final Item "+idU+" = plain(\""+id+"\""+(tipArg?", "+tipArg:"")+");";
  if(itype==="plainCr")         return"public static final Item "+idU+" = plainCr(\""+id+"\", \""+(craftRemainder&&craftRemainder!=="none"?craftRemainder:"minecraft:stick")+"\""+(tipArg?", "+tipArg:"")+");";
  if(itype==="ingredientBowl")  return"public static final Item "+idU+" = ingredientBowl(\""+id+"\");";
  if(itype==="ingredientBottle")return"public static final Item "+idU+" = ingredientBottle(\""+id+"\");";
  if(itype==="pipingBag")       return"public static final Item "+idU+" = pipingBag(\""+id+"\""+(tipArg?", "+tipArg:"")+");";
  if(itype==="bottle")          return"public static final Item "+idU+" = bottle(\""+id+"\", "+nut+", "+sat+t+");";
  if(itype==="bowlConsumable"||itype==="bowlFood") return"public static final Item "+idU+" = "+(crBowl?"bowlFoodCr":"bowlFood")+"(\""+id+"\", "+nut+", "+sat+t+");";
  if(itype==="stickConsumable"||itype==="stickFood") return"public static final Item "+idU+" = stickFood(\""+id+"\", "+nut+", "+sat+t+");";
  if(itype==="stickFoodCr")     return"public static final Item "+idU+" = stickFoodCr(\""+id+"\", "+nut+", "+sat+t+");";
  return"public static final Item "+idU+" = "+(fast?"fastFood":"food")+"(\""+id+"\", "+nut+", "+sat+t+");";
}
function buildBlockJava(item){
  const{id,blockType,sliceId,miniId,tooltipKeys,isCompat,compatKey}=item;if(!id)return"";
  const sId=sliceId||(id+"_slice"),idU=id.toUpperCase();
  const compatSuffix=isCompat&&compatKey?compatKey.replace("tooltip.compat.",""):null;
  const compatArg=compatSuffix?'"'+compatSuffix+'"':"null";
  const keyArgs=(tooltipKeys||[]).map(k=>'"'+k+'_ingredient"').join(", ");
  const hasTips=(tooltipKeys||[]).length>0||isCompat;
  const tipArg=hasTips?"tips("+compatArg+(keyArgs?", "+keyArgs:"")+")":(null);
  const tipSuffix=tipArg?", "+tipArg:"";
  if(blockType==="block_cake")     return "public static final Block "+idU+" = Block.cake(\""+id+"\", \""+sId+"\""+tipSuffix+");";
  if(blockType==="block_pie")      return "public static final Block "+idU+" = Block.cookedPie(\""+id+"\", \""+sId+"\""+tipSuffix+");\n\npublic static final Block RAW_"+idU+" = Block.rawPie(\"raw_"+id+"\");";
  if(blockType==="block_pizza")    return "public static final Block "+idU+" = Block.cookedPizza(\""+id+"\", \""+sId+"\""+tipSuffix+");\n\npublic static final Block RAW_"+idU+" = Block.rawPizza(\"raw_"+id+"\");";
  if(blockType==="block_raw_pie")  return "public static final Block "+idU+" = Block.rawPie(\""+id+"\""+tipSuffix+");";
  if(blockType==="block_raw_pizza")return "public static final Block "+idU+" = Block.rawPizza(\""+id+"\""+tipSuffix+");";
  if(blockType==="block_waffle"){const mId=miniId||(id.replace(/^waffle/,"mini_waffle")||"mini_"+id);return "public static final Block "+idU+" = Block.waffle(\""+id+"\", \""+mId+"\""+tipSuffix+");";}
  if(blockType==="block_gelatin")  return "public static final Block "+idU+" = Block.gelatin(\""+id+"\");";
  if(blockType==="block_cheese")   return "public static final Block "+idU+" = Block.cheese(\""+id+"\", \""+sId+"\");";
  if(blockType==="block_gyro_meat")return "public static final Block "+idU+" = Block.gyroMeat(\""+id+"\", \""+sId+"\");";
  if(blockType==="block_cake_base")return "public static final Block "+idU+" = Block.cakeBase(\""+id+"\""+tipSuffix+");";
  return "";
}
function buildSliceJava(item){
  const{sliceId,sliceJavaClass,sliceHasFood,sliceNutrition,sliceSaturation,sliceFast,sliceEffects,sliceShowEffectTooltip,tooltipKeys,isCompat,compatKey}=item;
  const sid=sliceId||(item.id+"_slice");if(!sid)return"";
  const itype="food";
  return buildItemJava({id:sid,itemType:itype,hasFood:sliceHasFood!==false,nutrition:sliceNutrition||2,saturation:sliceSaturation||0.3,fast:sliceFast!==false,effects:sliceEffects||[],showEffectTooltip:sliceShowEffectTooltip!==false,tooltipKeys:tooltipKeys||[],isCompat:isCompat||false,compatKey:compatKey||""});
}
function buildFluidJava(item){const{id,fluidFlowSlope,fluidFlowDecrease}=item;if(!id)return"";const flowArgs=(fluidFlowSlope>0&&fluidFlowDecrease>0)?", "+fluidFlowSlope+", "+fluidFlowDecrease:"";return "public static final Fluid "+id.toUpperCase()+"_FLUID = fluid(\""+id+"\""+flowArgs+");";}
function buildBottleJava(item){const{id,bottleHasFood,bottleNutrition,bottleSaturation,bottleFast,bottleEffects,bottleTooltipKeys,tooltipKeys,isCompat,compatKey}=item;return buildItemJava({id:id+"_bottle",itemType:"bottle",hasFood:bottleHasFood!==false,nutrition:bottleNutrition||4,saturation:bottleSaturation||1.0,fast:bottleFast||false,effects:bottleEffects||[],tooltipKeys:bottleTooltipKeys||tooltipKeys||[],isCompat:isCompat||false,compatKey:compatKey||""});}
function buildBowlJava(item){const{id,bowlHasFood,bowlNutrition,bowlSaturation,bowlFast,bowlEffects,bowlTooltipKeys,tooltipKeys,isCompat,compatKey}=item;return buildItemJava({id:id+"_bowl",itemType:"bowlFood",crBowl:true,hasFood:bowlHasFood!==false,nutrition:bowlNutrition||4,saturation:bowlSaturation||0.6,fast:bowlFast||false,effects:bowlEffects||[],tooltipKeys:bowlTooltipKeys||tooltipKeys||[],isCompat:isCompat||false,compatKey:compatKey||""});}
function buildModConfigNote(ids){const list=(Array.isArray(ids)?ids:[ids]).map(i=>"            \""+i+"\"").join(",\n");return "    public static final List<String> HIDE_ITEMS_DEFAULT = List.of(\n"+list+"\n    );";}
function mergeJava(existing,next,key){
  if(!existing)return next;
  if(!next)return existing;
  if(key&&key.endsWith("DisplayBlockRegistry.java")){
    const isExcluded=s=>s&&s.trimStart().startsWith("private static final Set<String> EXCLUDED_ITEMS");
    if(isExcluded(existing)&&isExcluded(next)){
      const extractIds=s=>{const m=s.match(/Set\.of\(([\s\S]*?)\)/);if(!m)return[];return m[1].split(",").map(x=>x.trim().replace(/^"|"$/g,"")).filter(Boolean);};
      const ids=[...new Set([...extractIds(existing),...extractIds(next)])];
      return buildDisplayBlockNote(ids);
    }
    if(isExcluded(existing)&&!isExcluded(next))return mergeDisplayBlockNote(existing,"")+"\n\n"+next;
    if(!isExcluded(existing)&&isExcluded(next))return existing+"\n\n"+next;
  }
  if(key&&(key.endsWith("ItemRegistry.java")||key.endsWith("BlockRegistry.java")||key.endsWith("FluidRegistry.java")))return existing+"\n"+next;
  return existing+"\n\n"+next;
}
function cond(id,modid){
  const neo=[...(modid?[{type:"neoforge:mod_loaded",modid}]:[]),{type:"createfood:enabled",id}];
  const fab=[...(modid?[{condition:"fabric:all_mods_loaded",values:[modid]}]:[]),{condition:"createfood:enabled",id}];
  return{"neoforge:conditions":neo,"fabric:load_conditions":fab};
}
function parseInputs(inputs){return inputs.map(inp=>{const v=inp.value.trim();if(inp.type==="item")return{item:v};if(inp.type==="fluid_tag"){const p=v.split(/\s*\|\s*/);const t=(p[0]||"").startsWith("c:")?p[0]:"c:"+p[0];return{type:"fluid_tag",amount:parseInt(p[1])||250,fluid_tag:t};}return{tag:v.startsWith("c:")?v:"c:"+v};});}
function buildRecipeJson(rec){
  const{recipeType:rt,outputId,outputCount:oc,isFluidOutput,fluidAmount:fa,inputs,experience:xp,cookingtime:ct,shapedPattern,shapedKey}=rec;
  if(!outputId)return null;
  const cid=outputId.includes(":")?outputId:"createfood:"+outputId;
  const baseId=outputId.includes(":")?outputId.split(":")[1]:outputId;
  const modid=rt.startsWith("create:")?"create":rt.startsWith("farmersdelight:")?"farmersdelight":null;
  const parsed=parseInputs(inputs),c=cond(baseId,modid);
  const iR=[{count:oc||1,item:{id:cid}}],fR=[{amount:fa||250,id:cid}];
  if(rt==="create:deploying")return{type:"create:deploying",ingredients:parsed,results:iR,...c};
  if(rt==="create:item_application"){const results=[{item:{id:cid}}];if(rec.byproductId){const bid=rec.byproductId.includes(":")?rec.byproductId:"createfood:"+rec.byproductId;results.push({item:{id:bid}});}return{type:"create:item_application",ingredients:parsed,results,...c};}
  if(rt==="create:mixing"||rt==="create:mixing_heated"){const h=rt==="create:mixing_heated";return{type:"create:mixing",...(h?{heat_requirement:"heated"}:{}),ingredients:parsed,results:isFluidOutput?fR:iR,...c};}
  if(rt==="create:compacting"||rt==="create:compacting_heated"){const h=rt==="create:compacting_heated";return{type:"create:compacting",...(h?{heat_requirement:"heated"}:{}),ingredients:parsed,results:isFluidOutput?fR:iR,...c};}
  if(rt==="create:pressing")return{type:"create:pressing",ingredients:parsed,results:iR,...c};
  if(rt==="create:milling")return{type:"create:milling",ingredients:parsed,results:iR,...c};
  if(rt==="farmersdelight:cooking"){const isBowlOutput=cid.endsWith("_bowl")||baseId.endsWith("_bowl");const container=isBowlOutput?{container:{count:1,id:"minecraft:bowl"}}:{};return{type:"farmersdelight:cooking",experience:xp,ingredients:parsed,recipe_book_tab:"meals",...container,result:{count:oc||1,id:cid},...c};}
  if(rt==="farmersdelight:cutting")return{type:"farmersdelight:cutting",ingredients:parsed,tool:{tag:"c:tools/knife"},result:[{item:{count:oc||1,id:cid}}],...c};
  if(rt==="minecraft:crafting_shapeless")return{type:"minecraft:crafting_shapeless",ingredients:parsed,result:{count:oc||1,id:cid},...c};
  if(rt==="minecraft:crafting_shaped"){
    const letters="ABCDEFGHI";const keyMap={};const pattern=[];
    (shapedPattern||["XXX","XXX","XXX"]).forEach(row=>{let r="";for(const ch of row){if(ch===" ")r+=" ";else{r+=ch;}}pattern.push(r);});
    const usedKeys={};(shapedKey||[]).forEach(k=>{if(k.letter&&k.input)usedKeys[k.letter]=parseInputs([{type:k.inputType||"tag",value:k.input}])[0];});
    return{type:"minecraft:crafting_shaped",pattern:shapedPattern||["XXX","XXX","XXX"],key:usedKeys,result:{count:oc||1,id:cid},...c};
  }
  if(["minecraft:smelting","minecraft:smoking","minecraft:campfire_cooking"].includes(rt))return{type:rt,category:"food",cookingtime:ct,experience:xp,ingredient:parsed[0],result:{count:oc||1,id:cid},...c};
  if(rt==="create:filling")return{type:"create:filling",ingredients:parsed,results:iR,...c};
  if(rt==="create:emptying")return{type:"create:emptying",ingredients:parsed,results:[...iR,...fR],...c};
  return null;
}
function recipeFilePath(rec){const rt=RECIPE_TYPES.find(r=>r.id===rec.recipeType);if(!rt||!rec.outputId)return null;const base=rec.outputId.includes(":")?rec.outputId.split(":")[1]:rec.outputId;const fluidInputs=(rec.inputs||[]).filter(i=>i.type==="fluid_tag");const fluidSuffix=fluidInputs.length>0?"_"+fluidInputs.map(i=>i.value.split("|")[0].replace(/^c:/,"").trim()).join("_"):"";return "data/createfood/recipe/"+rt.dir+"/"+base+rt.suffix+fluidSuffix+(rec.altSuffix?"_"+rec.altSuffix:"")+".json";}
const genBottleFilling=id=>({type:"create:filling",ingredients:[{item:"minecraft:glass_bottle"},{type:"fluid_tag",amount:250,fluid_tag:"c:"+id}],results:[{item:{id:"createfood:"+id+"_bottle"}}],"neoforge:conditions":[{type:"neoforge:mod_loaded",modid:"create"},{type:"createfood:enabled",id:id+"_bottle"}],"fabric:load_conditions":[{condition:"fabric:all_mods_loaded",values:["create"]},{condition:"createfood:enabled",id:id+"_bottle"}]});
const genBottleEmptying=id=>({type:"create:emptying",ingredients:[{tag:"c:"+id+"_bottle"}],results:[{item:{id:"minecraft:glass_bottle"}},{amount:250,id:"createfood:"+id}],"neoforge:conditions":[{type:"neoforge:mod_loaded",modid:"create"},{type:"createfood:enabled",id}],"fabric:load_conditions":[{condition:"fabric:all_mods_loaded",values:["create"]},{condition:"createfood:enabled",id}]});
const genBowlFilling=id=>({type:"create:filling",ingredients:[{item:"minecraft:bowl"},{type:"fluid_tag",amount:333,fluid_tag:"c:"+id}],results:[{item:{id:"createfood:"+id+"_bowl"}}],"neoforge:conditions":[{type:"neoforge:mod_loaded",modid:"create"},{type:"createfood:enabled",id:id+"_bowl"}],"fabric:load_conditions":[{condition:"fabric:all_mods_loaded",values:["create"]},{condition:"createfood:enabled",id:id+"_bowl"}]});
const genBowlEmptying=id=>({type:"create:emptying",ingredients:[{tag:"c:"+id+"_bowl"}],results:[{item:{id:"minecraft:bowl"}},{amount:333,id:"createfood:"+id}],"neoforge:conditions":[{type:"neoforge:mod_loaded",modid:"create"},{type:"createfood:enabled",id}],"fabric:load_conditions":[{condition:"fabric:all_mods_loaded",values:["create"]},{condition:"createfood:enabled",id}]});
const genBottleFromBucket=id=>({type:"minecraft:crafting_shapeless",ingredients:[{tag:"c:"+id+"_bucket"},...Array(4).fill({item:"minecraft:glass_bottle"})],result:{id:"createfood:"+id+"_bottle",count:4},"neoforge:conditions":[{type:"createfood:enabled",id:id+"_bottle"}],"fabric:load_conditions":[{condition:"createfood:enabled",id:id+"_bottle"}]});
const genBucketFromBottles=id=>({type:"minecraft:crafting_shapeless",ingredients:[{item:"minecraft:bucket"},...Array(4).fill({tag:"c:"+id+"_bottle"})],result:{id:"createfood:"+id+"_bucket"},"neoforge:conditions":[{type:"createfood:enabled",id:id+"_bottle"}],"fabric:load_conditions":[{condition:"createfood:enabled",id:id+"_bottle"}]});
const genBowlFromBucket=id=>({type:"minecraft:crafting_shapeless",ingredients:[{tag:"c:"+id+"_bucket"},...Array(3).fill({item:"minecraft:bowl"})],result:{id:"createfood:"+id+"_bowl",count:3},"neoforge:conditions":[{type:"createfood:enabled",id:id+"_bowl"}],"fabric:load_conditions":[{condition:"createfood:enabled",id:id+"_bowl"}]});
const genBucketFromBowls=id=>({type:"minecraft:crafting_shapeless",ingredients:[{item:"minecraft:bucket"},...Array(3).fill({tag:"c:"+id+"_bowl"})],result:{id:"createfood:"+id+"_bucket"},"neoforge:conditions":[{type:"createfood:enabled",id:id+"_bowl"}],"fabric:load_conditions":[{condition:"createfood:enabled",id:id+"_bowl"}]});

function buildItemFiles(item){
  const{id:rawId,displayName,registrationType,blockType,sliceId,createBottle,tooltipKeys,customTooltipKeys,isCompat,hasDisplayBlock,displayConfigs,hasVariants,varPattern,varNamePat,variants:varVariants}=item;
  const{ns,localId,isCF}=parseNs(rawId);const id=localId;
  const files={};if(!id)return files;
  const isItem=registrationType==="item",isBlock=registrationType==="block",isFluid=registrationType==="fluid";
  const effSlice=sliceId||(id+"_slice");const isRaw=blockType==="block_raw_pie"||blockType==="block_raw_pizza";
  const isCake=blockType==="block_cake";const isPieOrPizza=blockType==="block_pie"||blockType==="block_pizza";const sliceCount=isCake?7:4;
  const autoSet=new Set();const variantSet=new Set();
  const addFile=(k,v,auto=false,variant=false)=>{files[k]=v;if(auto)autoSet.add(k);if(variant)variantSet.add(k);};
  const lang={};
  if(isItem)lang["item."+ns+"."+id]=displayName||id;
  if(isBlock){lang["block."+ns+"."+id]=displayName||id;if(!isRaw)lang["item."+ns+"."+effSlice]=displayName?"Slice of "+displayName:effSlice;}
  if(isFluid){
    lang["fluid_type."+ns+"."+id]=(displayName||id)+" Fluid"; // #19
    lang["block."+ns+"."+id+"_block"]=(displayName||id)+" Fluid";
    lang["item."+ns+"."+id+"_bucket"]=(displayName||id)+" Bucket";
    if(createBottle)lang["item."+ns+"."+id+"_bottle"]=displayName||id;
    if(item.createBowl)lang["item."+ns+"."+id+"_bowl"]=displayName||id;
  }
  (customTooltipKeys||[]).filter(c=>(tooltipKeys||[]).includes(c.key)).forEach(c=>{lang["tooltip."+ns+"."+c.key+"_ingredient"]=" + "+c.display;});
  (item.customCompatKeys||[]).forEach(c=>{lang["tooltip.compat."+c.key]=c.display;});
  if(Object.keys(lang).length)addFile("assets/"+ns+"/lang/en_us.json",lang);
  if(!isCF){
    Object.assign(files,files);
    if(hasVariants&&varVariants&&varVariants.length)_addVariantFiles(files,autoSet,variantSet,item,id,ns,isItem,isBlock,isFluid,isCF,isRaw,isCake,sliceCount,isPieOrPizza,createBottle);
    files._autoSet=autoSet;files._variantSet=variantSet;return files;
  }
  const addJava=(k,v)=>{files[k]=files[k]?mergeJava(files[k],v,k):v;};
  if(isItem){const java=buildItemJava({...item,id});if(java)addJava(ITEM_REG,java);if(isCompat)addFile(MOD_CFG,buildModConfigNote(id));if(!hasDisplayBlock&&!isAutoDisplayBlock(id)){const cur=files[DISP_REG];files[DISP_REG]=cur?mergeDisplayBlockNote(cur,id):buildDisplayBlockNote([id]);}if(hasDisplayBlock&&!isAutoDisplayBlock(id)&&displayConfigs&&displayConfigs.length)addFile(DISP_REG,buildDisplayBlockRegistration(id,displayConfigs));}
  if(isBlock){
    if(!isRaw){
      const sj=buildSliceJava({...item,id,sliceId:effSlice});if(sj)addJava(ITEM_REG,sj);
    }

    const bj=buildBlockJava({...item,id,sliceId:effSlice});if(bj)addJava(BLOCK_REG,bj);
    if(isCompat)addFile(MOD_CFG,buildModConfigNote(id));
  }
  if(isFluid){
    const fj=buildFluidJava({...item,id});if(fj)addFile(FLUID_REG,fj);
    if(createBottle){
      const bj2=buildBottleJava({...item,id});if(bj2)addJava(ITEM_REG,bj2);
    }
    if(item.createBowl){
      const bwj=buildBowlJava({...item,id});if(bwj)addJava(ITEM_REG,bwj);
    }
  }
  if(hasVariants&&varVariants&&varVariants.length)_addVariantFiles(files,autoSet,variantSet,item,id,ns,isItem,isBlock,isFluid,isCF,isRaw,isCake,sliceCount,isPieOrPizza,createBottle);
  files._autoSet=autoSet;files._variantSet=variantSet;return files;
}
function _addVariantFiles(files,autoSet,variantSet,item,baseId,ns,isItem,isBlock,isFluid,isCF,isRaw,isCake,sliceCount,isPieOrPizza,createBottle){
  const{variants:varVariants,isCompat}=item;
  const addFile=(k,v,auto=false,variant=false)=>{files[k]=v;if(auto)autoSet.add(k);if(variant)variantSet.add(k);};
  const addJava=(k,v)=>{files[k]=files[k]?mergeJava(files[k],v,k):v;variantSet.add(k);};
  const langKey="assets/"+ns+"/lang/en_us.json";const lang=files[langKey]||{};
  const vBlockLines=[];const vItemLines=[];
  (varVariants||[]).forEach(v=>{
    const{ns:vns,localId:vid}=parseNs(v.id);
    if(isItem){
      lang["item."+vns+"."+vid]=v.displayName;
    }
    if(isBlock&&!isRaw){
      lang["block."+vns+"."+vid]=v.displayName;
      lang["item."+vns+"."+vid+"_slice"]="Slice of "+v.displayName;
    }
    if(isBlock&&isRaw){
      lang["block."+vns+"."+vid]=v.displayName;
    }
    if(isFluid){
      lang["fluid_type."+vns+"."+vid]=v.displayName+" Fluid";
      lang["block."+vns+"."+vid+"_block"]=v.displayName+" Fluid";
      lang["item."+vns+"."+vid+"_bucket"]=v.displayName+" Bucket";
      if(createBottle){
        lang["item."+vns+"."+vid+"_bottle"]=v.displayName;
      }
      if(item.createBowl){
        lang["item."+vns+"."+vid+"_bowl"]=v.displayName;
      }
    }
    if(isCF){
      const vTipKeys=v.tooltipKeys!=null?v.tooltipKeys:item.tooltipKeys||[];
      if(isBlock&&!isRaw){const bj=buildBlockJava({...item,id:vid,sliceId:vid+"_slice"});if(bj)vBlockLines.push(bj);const sj=buildSliceJava({...item,id:vid,sliceId:vid+"_slice",sliceEffects:v.effects||item.sliceEffects||[],tooltipKeys:vTipKeys});if(sj)vItemLines.push(sj);}
      else if(isFluid){const fj=buildFluidJava({...item,id:vid});if(fj)addJava(FLUID_REG,fj);if(createBottle){const baseNut=item.bottleNutrition||4;const baseSat=item.bottleSaturation||1.0;const bj2=buildBottleJava({...item,id:vid,bottleNutrition:v.nutrition!=null?v.nutrition:baseNut,bottleSaturation:v.saturation!=null?v.saturation:baseSat,bottleEffects:v.effects||item.bottleEffects||[],bottleTooltipKeys:v.bottleTooltipKeys||vTipKeys});if(bj2)vItemLines.push(bj2);}if(item.createBowl){const baseBwNut=item.bowlNutrition||4;const baseBwSat=item.bowlSaturation||0.6;const bwj=buildBowlJava({...item,id:vid,bowlNutrition:v.bowlNutrition!=null?v.bowlNutrition:baseBwNut,bowlSaturation:v.bowlSaturation!=null?v.bowlSaturation:baseBwSat,bowlEffects:v.bowlEffects||item.bowlEffects||[],bowlTooltipKeys:v.bowlTooltipKeys||vTipKeys});if(bwj)vItemLines.push(bwj);}}
      else{const vj=buildItemJava({...item,id:vid,displayName:v.displayName,nutrition:v.nutrition,saturation:v.saturation,effects:v.effects||[],tooltipKeys:vTipKeys,isCompat:false,compatKey:""});if(vj)vItemLines.push(vj);
        if(item.hasDisplayBlock&&!isAutoDisplayBlock(vid)&&item.displayConfigs&&item.displayConfigs.length){addJava(DISP_REG,buildDisplayBlockRegistration(vid,item.displayConfigs));}
        else if(!item.hasDisplayBlock&&!isAutoDisplayBlock(vid)){const cur=files[DISP_REG];files[DISP_REG]=cur?mergeDisplayBlockNote(cur,vid):buildDisplayBlockNote([vid]);variantSet.add(DISP_REG);}}    }
  });
  files[langKey]=lang;
  if(isCF){
    vBlockLines.forEach(j=>addJava(BLOCK_REG,j));
    vItemLines.forEach(j=>addJava(ITEM_REG,j));
    if(isCompat&&item.compatKey){
      const varIds=(varVariants||[]).map(v=>parseNs(v.id).localId).filter(Boolean);
      files[MOD_CFG]=buildModConfigNote([baseId,...varIds]);
    }
  }
}

function buildAutoRecipeFiles(item){
  const{id:rawId,registrationType,blockType,sliceId,createBottle,hasVariants,variants}=item;
  const{ns,localId,isCF}=parseNs(rawId);const id=localId;
  if(!id||!isCF)return{};
  const files={};
  const isBlock=registrationType==="block";const isFluid=registrationType==="fluid";
  const isRaw=blockType==="block_raw_pie"||blockType==="block_raw_pizza";
  const isCake=blockType==="block_cake";const sliceCount=isCake?7:4;
  const effSlice=sliceId||(id+"_slice");
  if(isFluid&&createBottle){
    files["data/createfood/recipe/create/filling/"+id+"_bottle_from_filling.json"]=genBottleFilling(id);
    files["data/createfood/recipe/create/emptying/"+id+"_fluid_from_emptying_bottle.json"]=genBottleEmptying(id);
    files["data/createfood/recipe/minecraft/crafting/"+id+"_bottle_from_crafting.json"]=genBottleFromBucket(id);
    files["data/createfood/recipe/minecraft/crafting/"+id+"_bucket_from_crafting.json"]=genBucketFromBottles(id);
  }
  if(isFluid&&item.createBowl){
    files["data/createfood/recipe/create/filling/"+id+"_bowl_from_filling.json"]=genBowlFilling(id);
    files["data/createfood/recipe/create/emptying/"+id+"_fluid_from_emptying_bowl.json"]=genBowlEmptying(id);
    files["data/createfood/recipe/minecraft/crafting/"+id+"_bowl_from_crafting.json"]=genBowlFromBucket(id);
    files["data/createfood/recipe/minecraft/crafting/"+id+"_bucket_from_crafting.json"]=genBucketFromBowls(id);
  }
  if(isBlock&&!isRaw){
    files["data/createfood/recipe/farmersdelight/cutting/"+effSlice+"_from_cutting.json"]=genBlockCuttingRecipe(id,effSlice,sliceCount,ns);
    files["data/createfood/recipe/minecraft/crafting/"+id+"_from_crafting.json"]=genSlicesCombineRecipe(id,effSlice,sliceCount,ns);
  }
  if(hasVariants&&variants&&variants.length){
    variants.forEach(v=>{
      const{ns:vns,localId:vid}=parseNs(v.id);
      if(isBlock&&!isRaw){
        files["data/createfood/recipe/farmersdelight/cutting/"+vid+"_slice_from_cutting.json"]=genBlockCuttingRecipe(vid,vid+"_slice",sliceCount,vns);
        files["data/createfood/recipe/minecraft/crafting/"+vid+"_from_crafting.json"]=genSlicesCombineRecipe(vid,vid+"_slice",sliceCount,vns);
      }
      if(isFluid&&createBottle){
        files["data/createfood/recipe/create/filling/"+vid+"_bottle_from_filling.json"]=genBottleFilling(vid);
        files["data/createfood/recipe/create/emptying/"+vid+"_fluid_from_emptying_bottle.json"]=genBottleEmptying(vid);
        files["data/createfood/recipe/minecraft/crafting/"+vid+"_bottle_from_crafting.json"]=genBottleFromBucket(vid);
        files["data/createfood/recipe/minecraft/crafting/"+vid+"_bucket_from_crafting.json"]=genBucketFromBottles(vid);
      }
      if(isFluid&&item.createBowl){
        files["data/createfood/recipe/create/filling/"+vid+"_bowl_from_filling.json"]=genBowlFilling(vid);
        files["data/createfood/recipe/create/emptying/"+vid+"_fluid_from_emptying_bowl.json"]=genBowlEmptying(vid);
        files["data/createfood/recipe/minecraft/crafting/"+vid+"_bowl_from_crafting.json"]=genBowlFromBucket(vid);
        files["data/createfood/recipe/minecraft/crafting/"+vid+"_bucket_from_crafting.json"]=genBucketFromBowls(vid);
      }
    });
  }
  return files;
}

async function loadJSZip(){if(window.JSZip)return window.JSZip;return new Promise((r,j)=>{const s=document.createElement("script");s.src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js";s.onload=()=>r(window.JSZip);s.onerror=j;document.head.appendChild(s);});}
async function downloadZip(files,name){const Z=await loadJSZip(),z=new Z();for(const[p,c]of Object.entries(files)){if(p==="_autoSet"||p==="_variantSet")continue;z.file(p,typeof c==="string"?c:JSON.stringify(c,null,2)+"\n");}const b=await z.generateAsync({type:"blob"}),u=URL.createObjectURL(b),a=document.createElement("a");a.href=u;a.download=name;a.click();URL.revokeObjectURL(u);}
function dlFile(fn,c){const s=typeof c==="string"?c:JSON.stringify(c,null,2)+"\n",b=new Blob([s],{type:"text/plain"}),u=URL.createObjectURL(b),a=document.createElement("a");a.href=u;a.download=fn;a.click();URL.revokeObjectURL(u);}

const BLANK_REC=()=>({uid:Math.random().toString(36).slice(2),recipeType:"create:deploying",outputId:"",outputCount:1,isFluidOutput:false,fluidAmount:250,inputs:[{type:"tag",value:""}],experience:0.0,cookingtime:200,altSuffix:"",byproductId:"",shapedPattern:["XXX","XXX","XXX"],shapedKey:[{letter:"X",inputType:"tag",input:""}]});
function parseImportedJson(fn,txt){try{const o=JSON.parse(txt);if(o.type&&(o.results||o.result||o.ingredient)){const ia=Array.isArray(o.ingredients)?o.ingredients:(o.ingredient?[o.ingredient]:[]);const inputs=ia.map(i=>{if(i.fluid_tag)return{type:"fluid_tag",value:i.fluid_tag+"|"+(i.amount||250)};if(i.tag)return{type:"tag",value:i.tag};if(i.item)return{type:"item",value:i.item};return{type:"tag",value:""};});const oid=((o.results&&o.results[0]?(o.results[0].item?o.results[0].item.id:o.results[0].id):(o.result?o.result.id:""))||"").replace("createfood:","");const rtype=o.type==="create:mixing"&&o.heat_requirement==="heated"?"create:mixing_heated":o.type==="create:compacting"&&o.heat_requirement==="heated"?"create:compacting_heated":o.type;const cnt=((o.results&&o.results[0]?o.results[0].count:(o.result?o.result.count:1))||1);const byproductId=(o.results&&o.results[1]&&o.results[1].item?o.results[1].item.id:"").replace("createfood:","");return{type:"recipe",rec:{...BLANK_REC(),recipeType:rtype,outputId:oid,outputCount:cnt,isFluidOutput:!!(o.results&&o.results[0]&&o.results[0].amount),fluidAmount:(o.results&&o.results[0]&&o.results[0].amount)||250,inputs,experience:o.experience||0.0,cookingtime:o.cookingtime||200,byproductId}};}if(o.values!==undefined)return{type:"tag",ids:(o.values||[]).map(v=>(v.id||v).replace("createfood:",""))};if(Object.keys(o).some(k=>k.startsWith("item.createfood."))){const items={};for(const[k,v]of Object.entries(o))if(k.startsWith("item.createfood."))items[k.replace("item.createfood.","")]=v;return{type:"lang",items};}}catch(e){}return null;}
async function importFromFiles(fileList,{onImportRecipe,onImportItem,setLog,onBatchImport}){
  const results=[];
  const entries=[];
  for(const file of fileList){
    if(file.name.endsWith(".zip")){
      try{
        const Z=await loadJSZip();const z=await Z.loadAsync(file);
        for(const[name,entry]of Object.entries(z.files)){
          if(!entry.dir)entries.push({name,text:await entry.async("string")});
        }
        results.push("i "+file.name+": opened zip with "+Object.keys(z.files).filter(k=>!z.files[k].dir).length+" files");
      }catch(e){results.push("? "+file.name+": zip error — "+e.message);}
    } else {
      const text=await new Promise(r=>{const fr=new FileReader();fr.onload=e=>r(e.target.result);fr.readAsText(file);});
      entries.push({name:file.name,text});
    }
  }
  let itemUpdates={};const recipeList=[];
  for(const{name,text}of entries){
    try{
      const o=JSON.parse(text);
      // ── Spec batch format ──────────────────────────────────────────────
      if(Array.isArray(o.items)&&o.items.length>0&&o.items[0]?.id){
        const EFFECT_SHORT={"Comfort":"ModEffectCategories.COMFORT","Nourishment":"ModEffectCategories.NOURISHMENT","Vitality":"ModEffectCategories.VITALITY","Satiation":"ModEffectCategories.SATIATION","Sustenance":"ModEffectCategories.SUSTENANCE","Feast":"ModEffectCategories.FEAST","Sugar Rush":"ModEffectCategories.SUGAR_RUSH","Rested":"ModEffectCategories.RESTED","Farmer's Blessing":"ModEffectCategories.FARMERS_BLESSING","Grandma's Blessing":"ModEffectCategories.GRANDMAS_BLESSING","Touch Absorb":"ModEffectCategories.TOUCH_ABSORB","Touch Regen":"ModEffectCategories.TOUCH_REGEN","Touch Poison":"ModEffectCategories.TOUCH_POISON","Touch Heal":"ModEffectCategories.TOUCH_HEAL","Combustion":"ModEffectCategories.COMBUSTION","Explosion":"ModEffectCategories.EXPLOSION","Repulsion":"ModEffectCategories.REPULSION","Lightning":"ModEffectCategories.LIGHTNING","Party Starter":"ModEffectCategories.PARTY_STARTER","Flight":"ModEffectCategories.FLIGHT","Mining":"ModEffectCategories.MINING","Pacify":"ModEffectCategories.PACIFY","Charisma":"ModEffectCategories.CHARISMA","Animal Charm":"ModEffectCategories.ANIMAL_CHARM","Balanced":"ModEffectCategories.BALANCED","Bonding":"ModEffectCategories.BONDING","Fortune":"ModEffectCategories.FORTUNE","Tough":"ModEffectCategories.TOUGH","Life Leech":"ModEffectCategories.LIFE_LEECH","Tundra Strider":"ModEffectCategories.TUNDRA_STRIDER","Warmth":"ModEffectCategories.WARMTH","Satiated Shield":"ModEffectCategories.SATIATED_SHIELD","Vigor":"ModEffectCategories.VIGOR","Rest":"ModEffectCategories.REST","Sulfur":"ModEffectCategories.REST","Mustard":"ModEffectCategories.MUSTARD","Preservation":"ModEffectCategories.PRESERVATION","Stout Heart":"ModEffectCategories.STOUT_HEART","Raging":"ModEffectCategories.RAGING","Sweet Heart":"ModEffectCategories.SWEET_HEART","Refreshed":"ModEffectCategories.REFRESHED","Well Served":"ModEffectCategories.WELL_SERVED","Stimulation":"ModEffectCategories.STIMULATION","Adrenaline":"ModEffectCategories.ADRENALINE","Berserk":"ModEffectCategories.BERSERK","Blood Clot":"ModEffectCategories.BLOOD_CLOT","Brimstone Vision":"ModEffectCategories.BRIMSTONE_VISION","Caffeinated":"ModEffectCategories.CAFFEINATED","Lava Walking":"ModEffectCategories.LAVA_WALKING","Perception":"ModEffectCategories.PERCEPTION","Water Walking":"ModEffectCategories.WATER_WALKING","Night Vision":"MobEffects.NIGHT_VISION","Regeneration":"MobEffects.REGENERATION","Water Breathing":"MobEffects.WATER_BREATHING","Fire Resistance":"MobEffects.FIRE_RESISTANCE","Slow Falling":"MobEffects.SLOW_FALLING","Speed":"MobEffects.MOVEMENT_SPEED","Haste":"MobEffects.DIG_SPEED","Glowing":"MobEffects.GLOWING","Luck":"MobEffects.LUCK","Strength":"MobEffects.DAMAGE_BOOST"};
        const mapFx=arr=>(arr||[]).map(e=>({effectId:EFFECT_SHORT[e.effect]||e.effect,duration:e.duration||300,amplifier:e.amplifier||0,chance:e.chance??1.0}));
        const BLOCK_TYPES=["block","block_cake","block_pie","block_pizza","block_raw_pie","block_raw_pizza","block_waffle","block_gelatin","block_cheese","block_gyro_meat","block_cake_base"];
        const bItemFiles={};const bRecipeFiles={};const bSnapshots=[];
        const mergeInto=(target,src)=>{const extractSetIds=s=>{const m=s&&s.match(/Set\.of\(([\s\S]*?)\)/);if(!m)return[];return m[1].split(",").map(x=>x.trim().replace(/^"|"$/g,"")).filter(Boolean);};const extractListIds=s=>{const m=s&&s.match(/List\.of\(([\s\S]*?)\)/);if(!m)return[];return m[1].split(",").map(x=>x.trim().replace(/^"|"$/g,"")).filter(Boolean);};for(const[k,v]of Object.entries(src)){if(k==="lang/en_us.json"||k.endsWith("/lang/en_us.json")){target[k]=typeof v==="object"?{...(target[k]||{}),...v}:v;}else if(k.endsWith("DisplayBlockRegistry.java")&&typeof v==="string"&&typeof target[k]==="string"){const isExcluded=s=>s&&s.trimStart().startsWith("private static final Set<String> EXCLUDED_ITEMS");if(isExcluded(target[k])&&isExcluded(v)){const ids=[...new Set([...extractSetIds(target[k]),...extractSetIds(v)])];target[k]=buildDisplayBlockNote(ids);}else{target[k]=target[k].includes(v)?target[k]:target[k]+"\n\n"+v;}}else if(k.endsWith("ModConfig.java")&&typeof v==="string"&&typeof target[k]==="string"){const isDisable=s=>s&&s.trimStart().startsWith("public static final List<String> HIDE_ITEMS_DEFAULT");if(isDisable(target[k])&&isDisable(v)){const ids=[...new Set([...extractListIds(target[k]),...extractListIds(v)])];target[k]=buildModConfigNote(ids);}else{target[k]=target[k].includes(v)?target[k]:target[k]+"\n\n"+v;}}else if(typeof v==="string"&&typeof target[k]==="string"){const sep=(k.endsWith("ItemRegistry.java")||k.endsWith("BlockRegistry.java")||k.endsWith("FluidRegistry.java"))?"\n":"\n\n";target[k]=target[k].includes(v)?target[k]:target[k]+sep+v;}else{target[k]=v;}}};
        for(const spec of o.items){
          const isFluid=spec.registrationType==="fluid"||spec.type==="fluid";
          const isBlock=spec.registrationType==="block"||BLOCK_TYPES.includes(spec.type);
          const registrationType=isFluid?"fluid":isBlock?"block":"item";
          const blockType=BLOCK_TYPES.includes(spec.type)?spec.type:(spec.blockType||"block_cake");
          const sliceFxArr=mapFx(spec.sliceEffects);
          const itemState={...BLANK_ITEM,id:spec.id||"",displayName:spec.displayName||spec.display||spec.id||"",registrationType,blockType,itemType:(!isFluid&&!isBlock)?(spec.type||"food"):BLANK_ITEM.itemType,nutrition:spec.nutrition??BLANK_ITEM.nutrition,saturation:spec.saturation??BLANK_ITEM.saturation,fast:spec.fast??false,crBowl:spec.crBowl??false,effects:mapFx(spec.effects),tooltipKeys:spec.tooltips||spec.tooltipKeys||[],customTooltipKeys:[],isCompat:spec.isCompat||false,compatKey:spec.compatKey||"",sliceId:spec.sliceId||"",sliceHasFood:spec.sliceNutrition!=null?true:BLANK_ITEM.sliceHasFood,sliceNutrition:spec.sliceNutrition??BLANK_ITEM.sliceNutrition,sliceSaturation:spec.sliceSaturation??BLANK_ITEM.sliceSaturation,sliceFast:spec.sliceFast??BLANK_ITEM.sliceFast,sliceEffects:sliceFxArr,sliceJavaClass:sliceFxArr.length?"ConsumableItem":"Item",createBottle:spec.createBottle||false,bottleNutrition:spec.bottleNutrition??4,bottleSaturation:spec.bottleSaturation??1.0,bottleEffects:mapFx(spec.bottleEffects),bottleTooltipKeys:spec.bottleTooltips||[],createBowl:spec.createBowl||false,bowlNutrition:spec.bowlNutrition??4,bowlSaturation:spec.bowlSaturation??0.6,bowlEffects:mapFx(spec.bowlEffects),bowlTooltipKeys:spec.bowlTooltips||[],hasVariants:!!(spec.variants?.length),variants:(spec.variants||[]).map(v=>({id:v.id,displayName:v.displayName||v.display||v.id,nutrition:v.nutrition,saturation:v.saturation,effects:mapFx(v.effects),tooltipKeys:v.tooltips||v.tooltipKeys||null}))};
          const effSlice=itemState.sliceId||(itemState.id?itemState.id+"_slice":"");
          const iFiles=buildItemFiles({...itemState,sliceId:effSlice});delete iFiles._autoSet;delete iFiles._variantSet;
          const arFiles=buildAutoRecipeFiles({...itemState,sliceId:effSlice});
          mergeInto(bItemFiles,iFiles);Object.assign(bRecipeFiles,arFiles);
          for(const r of(spec.recipes||[])){
            const rec={...BLANK_REC(),recipeType:r.type,outputId:r.output||spec.id,outputCount:r.count??1,inputs:(r.inputs||[]).map(inp=>({type:inp.type||"tag",value:inp.value||""})),altSuffix:r.suffix||"",isFluidOutput:r.fluidOutput||false,fluidAmount:r.fluidAmount||250,experience:r.experience||0,cookingtime:r.cookingtime||200,byproductId:r.byproductId||r.extraOutput||""};
            if(r.type==="minecraft:crafting_shaped"&&r.pattern&&r.keys){rec.shapedPattern=r.pattern;rec.shapedKey=Object.entries(r.keys).map(([letter,inp])=>({letter,inputType:inp.type||"tag",input:inp.value||""}));}
            const p=recipeFilePath(rec);const j=buildRecipeJson(rec);if(p&&j)bRecipeFiles[p]=j;
          }
          bSnapshots.push({...itemState});
        }
        if(onBatchImport)onBatchImport(bItemFiles,bRecipeFiles,bSnapshots);
        results.push("✓ Imported "+o.items.length+" item(s) from "+name);continue;
      }
      // ──────────────────────────────────────────────────────────────────
      if(name.includes("lang/en_us.json")){
        const ns=name.includes("/assets/")?(name.split("/assets/")[1]||"").split("/")[0]:"createfood";
        for(const[k,v]of Object.entries(o)){
          if(k.startsWith("item.")){const parts=k.split(".");if(parts.length>=3)itemUpdates={...itemUpdates,id:ns==="createfood"?parts.slice(2).join("."):ns+":"+parts.slice(2).join("."),displayName:v};}
          if(k.startsWith("block.")&&!k.includes("_block")){const parts=k.split(".");if(parts.length>=3){itemUpdates={...itemUpdates,id:ns==="createfood"?parts.slice(2).join("."):ns+":"+parts.slice(2).join("."),displayName:v,registrationType:"block"};}}
          if(k.startsWith("fluid_type.")){const parts=k.split(".");if(parts.length>=3){const fid=parts.slice(2).join(".");itemUpdates={...itemUpdates,id:ns==="createfood"?fid:ns+":"+fid,displayName:v.replace(/ Fluid$/,""),registrationType:"fluid"};}}
        }
        results.push("i lang: "+name);continue;
      }
      if(o.type&&(o.results||o.result||o.ingredient)){
        const p=parseImportedJson(name,text);
        if(p&&p.type==="recipe"){recipeList.push(p.rec);results.push("+ recipe: "+name);}
        if(o.type==="farmersdelight:cutting"&&name.includes("_from_cutting")){
          const slices=o.result?.[0]?.item?.count;
          if(slices===7)itemUpdates={...itemUpdates,registrationType:"block",blockType:"block_cake"};
          else if(slices===4)itemUpdates={...itemUpdates,registrationType:"block",blockType:"block_pie"};
        }
        if(o.type==="create:filling"&&name.includes("_bottle_from_filling")){
          itemUpdates={...itemUpdates,registrationType:"fluid",createBottle:true};
        }
        continue;
      }
      if(o.replace!==undefined&&o.values){
        const vals=(o.values||[]).map(v=>typeof v==="string"?v:v.id||"");
        if(name.includes("data/c/tags/fluid/"))itemUpdates={...itemUpdates,registrationType:"fluid"};
        results.push("i tag: "+name);continue;
      }
      if(o.variants){
        const hasBlock=Object.keys(o.variants).some(k=>k.includes("bites="));
        if(hasBlock&&Object.keys(o.variants).length>8)itemUpdates={...itemUpdates,registrationType:"block",blockType:"block_cake"};
        else if(hasBlock)itemUpdates={...itemUpdates,registrationType:"block",blockType:"block_pie"};
        else if(name.includes("_block.json"))itemUpdates={...itemUpdates,registrationType:"fluid"};
        else if(!name.includes("_slice"))itemUpdates={...itemUpdates,registrationType:"block"};
        results.push("i blockstate: "+name);continue;
      }
    }catch(e){}
  }
  if(Object.keys(itemUpdates).length>0){onImportItem(itemUpdates);results.push("✓ item state restored: "+JSON.stringify(Object.keys(itemUpdates).join(", ")));}
  recipeList.forEach(r=>onImportRecipe(r));
  if(recipeList.length>0)results.push("✓ imported "+recipeList.length+" recipe(s)");
  setLog(results);
}

export const S={bg:"#0c1220",panel:"#111827",border:"#1e2d42",accent:"#2563eb",text:"#e2e8f0",muted:"#64748b",faint:"#1e293b",code:"#0a1628",green:"#16a34a",yellow:"#ca8a04",red:"#dc2626",darkred:"#7f1d1d",setBlue:"#1e3a5f"};

function Inp({label,value,onChange,placeholder,note,mono,warn}){return (<div style={{marginBottom:10}}>{label&&<div style={{fontSize:11,color:S.muted,marginBottom:3}}>{label}</div>}<input value={value} onChange={e=>onChange(e.target.value)} placeholder={placeholder} style={{width:"100%",boxSizing:"border-box",background:S.faint,border:"1px solid "+(warn?S.yellow:S.border),color:S.text,borderRadius:5,padding:"6px 9px",fontSize:12,fontFamily:mono?"monospace":"inherit",outline:"none"}}/>{note&&<div style={{fontSize:10,color:warn?S.yellow:S.muted,marginTop:2}}>{note}</div>}</div>);}
export function Sel({label,value,onChange,options,note}){return (<div style={{marginBottom:10}}>{label&&<div style={{fontSize:11,color:S.muted,marginBottom:3}}>{label}</div>}<select value={value} onChange={e=>onChange(e.target.value)} style={{width:"100%",background:S.faint,border:"1px solid "+S.border,color:S.text,borderRadius:5,padding:"6px 9px",fontSize:12,fontFamily:"inherit",outline:"none"}}>{options.map(o=><option key={o.v||o.value||o} value={o.v||o.value||o}>{o.l||o.label||o}</option>)}</select>{note&&<div style={{fontSize:10,color:S.muted,marginTop:2}}>{note}</div>}</div>);}
function Num({label,value,onChange,min,max,step,note}){return (<div style={{marginBottom:10}}>{label&&<div style={{fontSize:11,color:S.muted,marginBottom:3}}>{label}</div>}<input type="number" value={value} onChange={e=>onChange(Number(e.target.value))} min={min} max={max} step={step||1} style={{width:"100%",boxSizing:"border-box",background:S.faint,border:"1px solid "+S.border,color:S.text,borderRadius:5,padding:"6px 9px",fontSize:12,fontFamily:"inherit",outline:"none"}}/>{note&&<div style={{fontSize:10,color:S.muted,marginTop:2}}>{note}</div>}</div>);}
function Tog({label,value,onChange}){return (<div style={{display:"flex",alignItems:"center",gap:8,marginBottom:8}}><button onClick={()=>onChange(!value)} style={{width:32,height:18,borderRadius:9,border:"none",cursor:"pointer",flexShrink:0,background:value?S.accent:S.border,position:"relative",transition:"background .15s"}}><span style={{position:"absolute",top:2,left:value?15:2,width:14,height:14,borderRadius:7,background:"#e2e8f0",transition:"left .15s"}}/></button><span style={{fontSize:12,color:S.text}}>{label}</span></div>);}
export function Btn({children,onClick,color,small,disabled,title}){return (<button onClick={onClick} disabled={disabled} title={title} style={{background:disabled?S.border:(color||S.accent),color:disabled?S.muted:S.text,border:"none",borderRadius:5,padding:small?"4px 10px":"6px 14px",fontSize:small?11:12,cursor:disabled?"default":"pointer",fontFamily:"inherit",opacity:disabled?0.6:1,whiteSpace:"nowrap"}}>{children}</button>);}
function CopyBtn({text}){const[ok,setOk]=useState(false);return (<button onClick={()=>{navigator.clipboard.writeText(text).catch(()=>{});setOk(true);setTimeout(()=>setOk(false),1600);}} style={{position:"absolute",top:6,right:6,background:ok?S.green:S.border,color:S.text,border:"none",borderRadius:4,padding:"2px 8px",fontSize:10,cursor:"pointer"}}>{ok?"✓":"copy"}</button>);}
function Warn({children}){return (<div style={{background:"#1c1400",border:"1px solid "+S.yellow,borderRadius:5,padding:"5px 9px",fontSize:11,color:"#fbbf24",marginBottom:6}}>{children}</div>);}
function Info({children}){return (<div style={{background:"#0f1e35",border:"1px solid "+S.accent,borderRadius:5,padding:"5px 9px",fontSize:11,color:"#93c5fd",marginBottom:6}}>{children}</div>);}
export function Chip({label,onDeselect,onDelete}){return (<span style={{display:"inline-flex",alignItems:"center",gap:3,background:"#172554",border:"1px solid "+S.accent,color:"#93c5fd",borderRadius:4,padding:"1px 4px 1px 7px",fontSize:10,marginRight:3,marginBottom:3}}><span onClick={onDeselect} style={{cursor:"pointer"}}>{label}</span>{onDelete&&<span onClick={onDelete} style={{color:"#f87171",fontWeight:700,padding:"0 2px",cursor:"pointer",lineHeight:1}}>✕</span>}</span>);}
export function Section({title,open,onToggle,children}){return (<div style={{marginBottom:4}}><div onClick={onToggle} style={{fontSize:10,fontWeight:700,color:S.accent,letterSpacing:"0.1em",textTransform:"uppercase",marginBottom:open?8:4,marginTop:2,paddingBottom:4,borderBottom:"1px solid "+S.border,cursor:"pointer",display:"flex",alignItems:"center",gap:6,userSelect:"none"}}><span style={{fontSize:9,opacity:0.7,display:"inline-block",transform:open?"rotate(90deg)":"rotate(0deg)",transition:"transform .15s"}}>▶</span>{title}</div>{open&&<div style={{marginBottom:8}}>{children}</div>}</div>);}
function CodeFile({label,content,downloadName,auto,variant}){const[open,setOpen]=useState(false);const str=typeof content==="string"?content:JSON.stringify(content,null,2);return (<div style={{marginBottom:8}}><div style={{display:"flex",alignItems:"center",gap:6,marginBottom:open?3:0}}><span onClick={()=>setOpen(!open)} style={{fontSize:9,opacity:0.6,cursor:"pointer",userSelect:"none",display:"inline-block",transform:open?"rotate(90deg)":"rotate(0deg)",transition:"transform .15s"}}>▶</span><div style={{fontSize:10,color:S.muted,fontFamily:"monospace",flex:1,wordBreak:"break-all",cursor:"pointer"}} title={label} onClick={()=>setOpen(!open)}>{label.length>100?label.slice(0,99)+"…":label}{auto&&<span style={{marginLeft:4,fontSize:9,color:"#a78bfa",border:"1px solid #a78bfa",borderRadius:3,padding:"0 3px"}}>auto</span>}{variant&&<span style={{marginLeft:4,fontSize:9,color:"#34d399",border:"1px solid #34d399",borderRadius:3,padding:"0 3px"}}>variant</span>}</div>{downloadName&&<Btn small onClick={e=>{e.stopPropagation();dlFile(downloadName,content);}}>⬇ Download</Btn>}</div>{open&&<div style={{position:"relative",background:S.code,borderRadius:5,padding:"10px 12px",fontSize:11,fontFamily:"monospace",color:"#7dd3fc",whiteSpace:"pre-wrap",wordBreak:"break-all"}}>{str}<CopyBtn text={str}/></div>}</div>);}
export function usePersistedSections(panelKey,defaults,globalSections,setGlobalSections){const stored=globalSections[panelKey]||{};const merged={...defaults,...stored};const toggle=name=>setGlobalSections(s=>({...s,[panelKey]:{...merged,[name]:!merged[name]}}));const openNext=name=>{const names=Object.keys(defaults);const idx=names.indexOf(name);if(idx>=0&&idx<names.length-1)setGlobalSections(s=>({...s,[panelKey]:{...merged,[names[idx+1]]:true}}));};return{openMap:merged,toggle,openNext};}

function EffectsSection({effects,hasFood,javaClass,showEffectTooltip,onShowEffect,onAdd,onRemove,onUpdate}){
  const canShow=(javaClass==="ConsumableItem"||javaClass==="DrinkableItem");
  const typeWarn=effects&&effects.length>0&&!canShow;
  return (<>
    {typeWarn&&<Warn>Effects tooltip display requires ConsumableItem or DrinkableItem item type.</Warn>}
    {canShow&&onShowEffect&&<div style={{marginBottom:6}}><Tog label="Show effect in tooltip" value={showEffectTooltip!==false} onChange={onShowEffect}/></div>}
    {effects.length>0&&!hasFood&&<Warn>Effects present but no food properties — effects require food properties to apply.</Warn>}
    {effects.map((ef,i)=>(<div key={i} style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:8,marginBottom:6}}><div style={{display:"flex",gap:4}}><div style={{flex:3}}><Sel label="Effect" value={ef.effectId} onChange={v=>onUpdate(i,"effectId",v)} options={EFFECTS.map(e=>({v:e.id,l:e.label}))}/></div><div style={{flex:2}}><Sel label="Duration" value={ef.duration} onChange={v=>onUpdate(i,"duration",Number(v))} options={(EFFECTS.find(e=>e.id===ef.effectId)?.durations||[1200]).map(d=>({v:String(d),l:d+"t"}))}/></div><div style={{flex:1}}><Num label="Chance" value={ef.chance??1.0} onChange={v=>onUpdate(i,"chance",Math.min(1,Math.max(0.05,Number(v))))} min={0.05} max={1.0} step={0.05} note={(ef.chance??1.0)<1.0?Math.round((ef.chance??1.0)*100)+"% chance":""}/></div><button onClick={()=>onRemove(i)} style={{background:"none",border:"none",color:S.red,cursor:"pointer",fontSize:13,padding:"0 4px",marginTop:20}}>✕</button></div></div>))}
    {effects.length<3&&<Btn small onClick={onAdd}>+ Add effect</Btn>}
    {effects.length>=3&&<Warn>3+ effects — confirm precedent.</Warn>}
  </>);
}
function FoodSection({hasFood,nutrition,saturation,fast,onToggle,onNut,onSat,onFast}){
  return (<><Tog label="Has food properties" value={hasFood} onChange={onToggle}/>{hasFood&&<><Tog label="Has fast property" value={fast} onChange={onFast}/>{fast&&<div style={{fontSize:10,color:S.muted,marginBottom:6}}>Eat animation skipped — use for snack-sized items only.</div>}<div style={{display:"flex",gap:8}}><div style={{flex:1}}><Num label="Nutrition" value={nutrition} onChange={onNut} min={1} max={20} note="Half-hunger icons restored. 20 = full bar."/></div><div style={{flex:1}}><Num label="Saturation" value={saturation} onChange={onSat} min={0} max={3} step={0.1} note="Saturation modifier. Added saturation = nutrition × modifier × 2."/></div></div></>}</>);}

function TooltipsSection({tooltipKeys,customTooltipKeys,tooltipSearch,newTooltipKey,newTooltipDisplay,isCompat,compatKey,customCompatKeys,newCompatKey,newCompatDisplay,showEffectTooltip,javaClass,effects,hasFood,onSet,onChange}){
  const allAvailKeys=[...BASE_TOOLTIP_KEYS,...customTooltipKeys.map(c=>c.key)];
  const filtered=allAvailKeys.filter(k=>k.includes((tooltipSearch||"").toLowerCase())&&!tooltipKeys.includes(k));
  const allCompatKeys=[...BUILTIN_COMPAT_KEYS,...(customCompatKeys||[]).map(c=>"tooltip.compat."+c.key)];
  const addKey=()=>{const k=newTooltipKey.trim().toLowerCase().replace(/\s+/g,"_");const d=newTooltipDisplay.trim();if(!k||customTooltipKeys.find(c=>c.key===k))return;onChange({customTooltipKeys:[...customTooltipKeys,{key:k,display:d||k}],tooltipKeys:[...tooltipKeys,k],newTooltipKey:"",newTooltipDisplay:""});};
  const addCKey=()=>{const k=newCompatKey.trim().toLowerCase().replace(/\s+/g,"_");const d=newCompatDisplay.trim();if(!k||(customCompatKeys||[]).find(c=>c.key===k))return;onChange({customCompatKeys:[...(customCompatKeys||[]),{key:k,display:d||k}],newCompatKey:"",newCompatDisplay:""});};
  return (<>
    {tooltipKeys.length>0&&<div style={{display:"flex",flexWrap:"wrap",marginBottom:6}}>{tooltipKeys.map(k=>{const isCust=!!customTooltipKeys.find(c=>c.key===k);return(<Chip key={k} label={k} onDeselect={()=>onSet("tooltipKeys",tooltipKeys.filter(x=>x!==k))} onDelete={isCust?()=>onChange({customTooltipKeys:customTooltipKeys.filter(c=>c.key!==k),tooltipKeys:tooltipKeys.filter(x=>x!==k)}):null}/>);})}</div>}
    <div style={{display:"flex",flexWrap:"wrap",gap:3,marginBottom:6}}>{filtered.map(k=><button key={k} onClick={()=>onSet("tooltipKeys",[...tooltipKeys,k])} style={{background:S.faint,border:"1px solid "+S.border,color:S.muted,borderRadius:3,padding:"1px 6px",fontSize:10,cursor:"pointer"}}>{k}{customTooltipKeys.find(c=>c.key===k)?" *":""}</button>)}</div>
    <input placeholder="Search…" value={tooltipSearch} onChange={e=>onSet("tooltipSearch",e.target.value)} style={{width:"100%",boxSizing:"border-box",background:S.faint,border:"1px solid "+S.border,color:S.text,borderRadius:5,padding:"5px 8px",fontSize:11,marginBottom:6,fontFamily:"inherit",outline:"none"}}/>
    <div style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"7px 9px"}}>
      <div style={{fontSize:10,color:S.muted,marginBottom:4}}>Ingredient</div>
      <div style={{display:"flex",gap:4}}><input value={newTooltipKey} onChange={e=>onSet("newTooltipKey",e.target.value)} placeholder="key_name" onKeyDown={e=>e.key==="Enter"&&addKey()} style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"monospace",outline:"none"}}/><input value={newTooltipDisplay} onChange={e=>onSet("newTooltipDisplay",e.target.value)} placeholder="Display Name" onKeyDown={e=>e.key==="Enter"&&addKey()} style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"inherit",outline:"none"}}/><Btn small onClick={addKey}>Add</Btn></div>
    </div>
  </>);
}

// ── IngredientsPicker — proper component so useState is valid with multiple variant cards ──
function IngredientsPicker({vKeys,vSearch,setVKeys,setSearch,allAvail}){
  const[open,setOpen]=useState(false);
  const avail=allAvail.filter(k=>!vKeys.includes(k)&&(!(vSearch||"").toLowerCase()||k.includes((vSearch||"").toLowerCase())));
  return(<div style={{marginTop:6,borderTop:"1px solid "+S.border,paddingTop:6}}>
    <div onClick={()=>setOpen(!open)} style={{display:"flex",alignItems:"center",gap:4,cursor:"pointer",userSelect:"none",marginBottom:open?4:0}}>
      <span style={{fontSize:8,color:S.muted,display:"inline-block",transform:open?"rotate(90deg)":"rotate(0deg)",transition:"transform .12s"}}>▶</span>
      <span style={{fontSize:10,color:S.muted}}>Ingredients{vKeys.length>0?` (${vKeys.length})`:""}</span>
    </div>
    {open&&<>
      {vKeys.length>0&&<div style={{display:"flex",flexWrap:"wrap",marginBottom:4}}>{vKeys.map(k=>(<Chip key={k} label={k} onDeselect={()=>setVKeys(vKeys.filter(x=>x!==k))} onDelete={null}/>))}</div>}
      <div style={{display:"flex",flexWrap:"wrap",gap:3,marginBottom:4}}>{avail.map(k=>(<button key={k} onClick={()=>setVKeys([...vKeys,k])} style={{background:S.code,border:"1px solid "+S.border,color:S.muted,borderRadius:3,padding:"1px 6px",fontSize:10,cursor:"pointer"}}>{k}</button>))}</div>
      <input placeholder="Search…" value={vSearch||""} onChange={e=>setSearch(e.target.value)} style={{width:"100%",boxSizing:"border-box",background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"3px 7px",fontSize:11,marginBottom:4,fontFamily:"inherit",outline:"none"}}/>
    </>}
  </div>);
}

const BLANK_ITEM={
  id:"",displayName:"",registrationType:"item",
  itemType:"food",nutrition:8,saturation:0.6,fast:false,
  craftRemainder:"none",
  crBowl:false,
  effects:[],showEffectTooltip:true,
  hasDisplayBlock:false,displayConfigs:[{type:"PLATE",maxStack:1,height:12,hasParticles:false}],
  blockType:"block_cake",sliceId:"",
  sliceJavaClass:"Item",sliceHasFood:true,sliceNutrition:2,sliceSaturation:0.3,sliceFast:true,
  sliceEffects:[],sliceShowEffectTooltip:true,
  fluidFlowSlope:0,fluidFlowDecrease:0,
  createBottle:false,
  bottleHasFood:true,bottleNutrition:4,bottleSaturation:1.0,bottleFast:false,
  bottleEffects:[],bottleShowEffectTooltip:true,bottleStackSize:16,
  createBowl:false,
  bowlHasFood:true,bowlNutrition:4,bowlSaturation:0.6,bowlFast:false,
  bowlEffects:[],bowlShowEffectTooltip:true,bowlStackSize:16,
  tooltipKeys:[],customTooltipKeys:[],tooltipSearch:"",newTooltipKey:"",newTooltipDisplay:"",
  bottleTooltipKeys:[],bottleTooltipSearch:"",
  bowlTooltipKeys:[],bowlTooltipSearch:"",
  isCompat:false,compatKey:"",customCompatKeys:[],newCompatKey:"",newCompatDisplay:"",
  hasVariants:false,varPattern:"",varNamePat:"",
  varNewId:"",varNewName:"",
  variants:[],
};
const ITEM_DEFAULTS={identity:true,registration:false,properties:false,effects:false,sliceprops:false,sliceeffects:false,bottleprops:false,bottleeffects:false,bottleingredients:false,bowlprops:false,bowleffects:false,bowlingredients:false,tooltips:false,displayblock:false,variations:false,files:true};
function checkBerry(s){return(s||"").toLowerCase().includes("sweet_berry");}

function ItemPanel({item,onChange,sections,setSections,onSave,onReset,hasSaved,allItemFiles}){
  const{id:rawId,displayName,registrationType,itemType,nutrition,saturation,fast,crBowl,
    craftRemainder,effects,showEffectTooltip,
    hasDisplayBlock,displayConfigs,blockType,sliceId,fluidFlowSlope,fluidFlowDecrease,createBottle,
    tooltipKeys,customTooltipKeys,tooltipSearch,newTooltipKey,newTooltipDisplay,
    bottleTooltipKeys,bottleTooltipSearch,
    bowlTooltipKeys,bowlTooltipSearch,
    isCompat,compatKey,customCompatKeys,newCompatKey,newCompatDisplay,
    sliceJavaClass,sliceHasFood,sliceNutrition,sliceSaturation,sliceFast,sliceEffects,sliceShowEffectTooltip,
    bottleHasFood,bottleNutrition,bottleSaturation,bottleFast,bottleEffects,bottleShowEffectTooltip,
    hasVariants,varPattern,varNamePat,varNewId,varNewName,variants}=item;
  const set=(k,v)=>onChange({...item,[k]:v});
  const{ns,localId,isCF}=parseNs(rawId);const id=localId;
  const isItem=registrationType==="item",isBlock=registrationType==="block",isFluid=registrationType==="fluid";
  const{openMap,toggle,openNext}=usePersistedSections("item",ITEM_DEFAULTS,sections,setSections);
  const effSlice=sliceId||(id?id+"_slice":"");const isRaw=blockType==="block_raw_pie"||blockType==="block_raw_pizza";
  const idWarn=checkBerry(rawId)?"Use berry, not sweet_berry":null;
  const nameWarn=checkBerry(displayName)?"Use Sweet Berry, not sweet_berry":null;

  const files=buildItemFiles({...item,sliceId:effSlice});
  const autoSet=files._autoSet||new Set();const variantSet=files._variantSet||new Set();

  const addDispConfig=()=>set("displayConfigs",[...(displayConfigs||[]),{type:"PLATE",maxStack:1,height:12,hasParticles:false}]);
  const remDispConfig=i=>set("displayConfigs",(displayConfigs||[]).filter((_,j)=>j!==i));
  const updDispConfig=(i,k,v)=>set("displayConfigs",(displayConfigs||[]).map((c,j)=>j===i?{...c,[k]:v}:c));

  const addVariant=()=>{
    const pat=varPattern||(id?"[v]_"+id:"[v]");
    const namePat2=varNamePat||(displayName?"[v] "+displayName:"[v]");
    const k=(varNewId||"").trim();const n=(varNewName||"").trim();
    if(!k)return;
    const vid=pat.replace(/\[v\]/g,k);
    const vname=n||namePat2.replace(/\[v\]/g,k);
    const baseNut=itemTypeHasFood(itemType||"food")?nutrition:sliceHasFood?sliceNutrition:bottleHasFood?bottleNutrition:4;
    const baseSat=itemTypeHasFood(itemType||"food")?saturation:sliceHasFood?sliceSaturation:bottleHasFood?bottleSaturation:0.5;
    const baseBwNut=item.bowlHasFood?item.bowlNutrition:4;
    const baseBwSat=item.bowlHasFood?item.bowlSaturation:0.5;
    const newV={id:vid,displayName:vname,nutrition:baseNut,saturation:baseSat,effects:[],bowlNutrition:baseBwNut,bowlSaturation:baseBwSat,bowlEffects:[],tooltipKeys:[...(item.tooltipKeys||[])]};
    onChange({...item,variants:[...(variants||[]),newV],varNewId:"",varNewName:""});
  };
  const removeVariant=i=>set("variants",(variants||[]).filter((_,j)=>j!==i));
  const updVariant=(i,k,v)=>set("variants",(variants||[]).map((v2,j)=>j===i?{...v2,[k]:v}:v2));
  const updVarEffect=(vi,ei,k,v)=>set("variants",(variants||[]).map((v2,j)=>j===vi?{...v2,effects:v2.effects.map((e,k2)=>k2===ei?{...e,[k]:v}:e)}:v2));
  const addVarEffect=vi=>set("variants",(variants||[]).map((v2,j)=>j===vi?{...v2,effects:[...(v2.effects||[]),{effectId:EFFECTS[0].id,duration:1200,amplifier:0,chance:1.0}]}:v2));
  const remVarEffect=(vi,ei)=>set("variants",(variants||[]).map((v2,j)=>j===vi?{...v2,effects:v2.effects.filter((_,k2)=>k2!==ei)}:v2));
  const updVarBowlEffect=(vi,ei,k,v)=>set("variants",(variants||[]).map((v2,j)=>j===vi?{...v2,bowlEffects:(v2.bowlEffects||[]).map((e,k2)=>k2===ei?{...e,[k]:v}:e)}:v2));
  const addVarBowlEffect=vi=>set("variants",(variants||[]).map((v2,j)=>j===vi?{...v2,bowlEffects:[...(v2.bowlEffects||[]),{effectId:EFFECTS[0].id,duration:1200,amplifier:0,chance:1.0}]}:v2));
  const remVarBowlEffect=(vi,ei)=>set("variants",(variants||[]).map((v2,j)=>j===vi?{...v2,bowlEffects:(v2.bowlEffects||[]).filter((_,k2)=>k2!==ei)}:v2));

  return (<div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:16,height:"100%",minHeight:0}}>
    <div style={{overflowY:"auto",paddingRight:4}}>
      <Section title="Identity" open={openMap.identity} onToggle={()=>toggle("identity")}>
        <Inp label="ID" value={rawId} onChange={v=>{set("id",v);if(v)openNext("identity");}} placeholder="beef_bun" warn={!!idWarn} note={idWarn||(!isCF?"Namespace: "+ns+" — registration files disabled":undefined)} mono/>
        <Inp label="Display Name" value={displayName} onChange={v=>{set("displayName",v);if(v)openNext("identity");}} placeholder="Beef Bun" warn={!!nameWarn} note={nameWarn||undefined}/>
        <Tog label="Requires optional mod ingredient" value={isCompat} onChange={v=>set("isCompat",v)}/>
        {isCompat&&<div style={{marginBottom:10}}>
          {compatKey&&<div style={{display:"flex",flexWrap:"wrap",marginBottom:6}}><Chip label={compatKey.replace("tooltip.compat.","")} onDeselect={()=>set("compatKey","")} onDelete={(customCompatKeys||[]).find(c=>"tooltip.compat."+c.key===compatKey)?()=>onChange({...item,customCompatKeys:(customCompatKeys||[]).filter(c=>"tooltip.compat."+c.key!==compatKey),compatKey:""}):null}/></div>}
          <div style={{display:"flex",flexWrap:"wrap",gap:3,marginBottom:6}}>{[...BUILTIN_COMPAT_KEYS,...(customCompatKeys||[]).map(c=>"tooltip.compat."+c.key)].filter(k=>k!==compatKey).map(k=><button key={k} onClick={()=>set("compatKey",k)} style={{background:S.faint,border:"1px solid "+S.border,color:S.muted,borderRadius:3,padding:"1px 6px",fontSize:10,cursor:"pointer"}}>{k.replace("tooltip.compat.","")}</button>)}</div>
          <div style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"7px 9px",marginBottom:8}}>
            <div style={{fontSize:10,color:S.muted,marginBottom:4}}>Mod Ingredient Key</div>
            <div style={{display:"flex",gap:4}}><input value={newCompatKey} onChange={e=>set("newCompatKey",e.target.value)} placeholder="key_name" onKeyDown={e=>e.key==="Enter"&&(()=>{const k=(newCompatKey||"").trim().toLowerCase().replace(/\s+/g,"_");const d=(newCompatDisplay||"").trim();if(!k||(customCompatKeys||[]).find(c=>c.key===k))return;onChange({...item,customCompatKeys:[...(customCompatKeys||[]),{key:k,display:d||k}],newCompatKey:"",newCompatDisplay:""});})()} style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"monospace",outline:"none"}}/><input value={newCompatDisplay} onChange={e=>set("newCompatDisplay",e.target.value)} placeholder="Mod Name" style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"inherit",outline:"none"}}/><Btn small onClick={()=>{const k=(newCompatKey||"").trim().toLowerCase().replace(/\s+/g,"_");const d=(newCompatDisplay||"").trim();if(!k||(customCompatKeys||[]).find(c=>c.key===k))return;onChange({...item,customCompatKeys:[...(customCompatKeys||[]),{key:k,display:d||k}],newCompatKey:"",newCompatDisplay:""});}}>Add</Btn></div>
            {(customCompatKeys||[]).length>0&&<div style={{marginTop:4,display:"flex",flexWrap:"wrap"}}>{(customCompatKeys||[]).map(c=><Chip key={c.key} label={c.key} onDeselect={()=>{}} onDelete={()=>onChange({...item,customCompatKeys:(customCompatKeys||[]).filter(x=>x.key!==c.key),compatKey:compatKey===("tooltip.compat."+c.key)?"":compatKey})}/>)}</div>}
          </div>
        </div>}
      </Section>
      <Section title="Registration" open={openMap.registration} onToggle={()=>toggle("registration")}>
        <div style={{display:"flex",gap:6,marginBottom:10}}>{["item","block","fluid"].map(t=><button key={t} onClick={()=>{set("registrationType",t);openNext("registration");}} style={{flex:1,background:registrationType===t?S.accent:S.faint,border:"1px solid "+(registrationType===t?S.accent:S.border),color:S.text,borderRadius:5,padding:"5px 0",fontSize:11,cursor:"pointer",fontFamily:"inherit",fontWeight:registrationType===t?600:400,textTransform:"capitalize"}}>{t}</button>)}</div>
        {isItem&&isCF&&<><Sel label="Item Type" value={itemType||"food"} onChange={v=>{set("itemType",v);openNext("registration");}} options={ITEM_TYPE_OPTIONS.map(o=>({v:o.v,l:o.l}))} note={itemTypeInfo(itemType||"food").desc}/>{itemType==="plainCr"&&<Sel label="Remainder" value={craftRemainder} onChange={v=>set("craftRemainder",v)} options={CRAFT_REMAIN}/>}{(itemType==="bowlFood"||itemType==="bowlConsumable")&&<Tog label="Craft remainder returns bowl" value={crBowl||false} onChange={v=>set("crBowl",v)}/>}</>}
        {isBlock&&isCF&&<><Sel label="Block Type" value={blockType} onChange={v=>set("blockType",v)} options={[{v:"block_cake",l:"Cake (7 bites)"},{v:"block_pie",l:"Pie / Cheesecake (4 slices)"},{v:"block_pizza",l:"Pizza / Waffle (4 slices)"},{v:"block_raw_pie",l:"Raw Pie"},{v:"block_raw_pizza",l:"Raw Pizza"}]} note={{"block_cake":"Placed cake consumed bite by bite. Generates 7-slice cutting recipe and a slice item. Stack 1.","block_pie":"4-slice pie or cheesecake. Generates a cooked block + raw block pair and a slice item.","block_pizza":"4-slice pizza or waffle. Generates a cooked block + raw block pair and a slice item.","block_raw_pie":"Standalone raw pie block only — no cooked form or slice. Use when the cooked block is registered separately.","block_raw_pizza":"Standalone raw pizza block only — no cooked form or slice. Use when the cooked block is registered separately."}[blockType]}/>{(blockType==="block_cake"||blockType==="block_pie"||blockType==="block_pizza")&&<Inp label="Slice ID" value={sliceId} onChange={v=>set("sliceId",v)} placeholder={id?id+"_slice":"id_slice"} mono note="Only set this if the slice ID doesn't follow the default pattern."/>}</>}
        {isFluid&&isCF&&<><div style={{display:"flex",gap:8}}><div style={{flex:1}}><Num label="Flow slope" value={fluidFlowSlope} onChange={v=>set("fluidFlowSlope",v)} min={0} max={5} note="Higher = flows less far sideways."/></div><div style={{flex:1}}><Num label="Flow decrease" value={fluidFlowDecrease} onChange={v=>set("fluidFlowDecrease",v)} min={0} max={8} note="Higher = shorter vertical drop."/></div></div><Tog label="Has bottle item" value={createBottle} onChange={v=>set("createBottle",v)}/><Tog label="Has bowl item" value={item.createBowl||false} onChange={v=>set("createBowl",v)}/></>}
        {!isCF&&<Info>Non-createfood namespace — lang and recipe files are generated; Java registration files are not.</Info>}
      </Section>
      {(()=>{const itype=itemType||"food";const typeHasFood=itemTypeHasFood(itype);const isConsumableType=itype==="consumable"||itype==="bowlConsumable";
        return isItem&&isCF&&typeHasFood?<><Section title="Properties" open={openMap.properties} onToggle={()=>toggle("properties")}><Tog label="Has fast property" value={fast} onChange={v=>set("fast",v)}/>{fast&&<div style={{fontSize:10,color:S.muted,marginBottom:6}}>Eat animation skipped — use for snack-sized items and single bites only.</div>}<div style={{display:"flex",gap:8}}><div style={{flex:1}}><Num label="Nutrition" value={nutrition} onChange={v=>set("nutrition",v)} min={1} max={20} note="Half-hunger icons restored. 20 = full bar."/></div><div style={{flex:1}}><Num label="Saturation" value={saturation} onChange={v=>set("saturation",v)} min={0} max={3} step={0.1} note="Saturation modifier. Added saturation = nutrition × modifier × 2."/></div></div></Section>
          <Section title="Effects" open={openMap.effects} onToggle={()=>toggle("effects")}><EffectsSection effects={effects} hasFood={true} javaClass={isConsumableType?"ConsumableItem":itype==="bottle"?"DrinkableItem":"Item"} showEffectTooltip={showEffectTooltip} onShowEffect={v=>set("showEffectTooltip",v)} onAdd={()=>set("effects",[...effects,{effectId:EFFECTS[0].id,duration:1200,amplifier:0,chance:1.0}])} onRemove={i=>set("effects",effects.filter((_,j)=>j!==i))} onUpdate={(i,k,v)=>set("effects",effects.map((e,j)=>j===i?{...e,[k]:v}:e))}/></Section></>:null;})()}
      {isBlock&&isCF&&!isRaw&&<>
        <Section title="Slice Properties" open={openMap.sliceprops} onToggle={()=>toggle("sliceprops")}>
          <FoodSection hasFood={sliceHasFood} nutrition={sliceNutrition} saturation={sliceSaturation} fast={sliceFast} onToggle={v=>set("sliceHasFood",v)} onNut={v=>set("sliceNutrition",v)} onSat={v=>set("sliceSaturation",v)} onFast={v=>set("sliceFast",v)}/>
        </Section>
        <Section title="Slice Effects" open={openMap.sliceeffects} onToggle={()=>toggle("sliceeffects")}>
          <EffectsSection effects={sliceEffects||[]} hasFood={sliceHasFood} javaClass={(sliceEffects||[]).length>0?"ConsumableItem":"Item"} showEffectTooltip={sliceShowEffectTooltip} onShowEffect={v=>set("sliceShowEffectTooltip",v)} onAdd={()=>onChange({...item,sliceEffects:[...(sliceEffects||[]),{effectId:EFFECTS[0].id,duration:1200,amplifier:0,chance:1.0}],sliceJavaClass:"ConsumableItem"})} onRemove={i=>{const next=(sliceEffects||[]).filter((_,j)=>j!==i);set("sliceEffects",next);if(!next.length)set("sliceJavaClass","Item");}} onUpdate={(i,k,v)=>set("sliceEffects",(sliceEffects||[]).map((e,j)=>j===i?{...e,[k]:v}:e))}/>
        </Section>
      </>}
      {isFluid&&isCF&&createBottle&&<>
        <Section title="Bottle Properties" open={openMap.bottleprops} onToggle={()=>toggle("bottleprops")}>
          <Info>DrinkableItem registered for the bottle form of this fluid. Stack size is always 16.</Info>
          <FoodSection hasFood={bottleHasFood} nutrition={bottleNutrition} saturation={bottleSaturation} fast={bottleFast} onToggle={v=>set("bottleHasFood",v)} onNut={v=>set("bottleNutrition",v)} onSat={v=>set("bottleSaturation",v)} onFast={v=>set("bottleFast",v)}/>
        </Section>
        {bottleHasFood&&<Section title="Bottle Effects" open={openMap.bottleeffects} onToggle={()=>toggle("bottleeffects")}>
          <EffectsSection effects={bottleEffects||[]} hasFood={bottleHasFood} javaClass="DrinkableItem" showEffectTooltip={bottleShowEffectTooltip} onShowEffect={v=>set("bottleShowEffectTooltip",v)} onAdd={()=>set("bottleEffects",[...(bottleEffects||[]),{effectId:EFFECTS[0].id,duration:1200,amplifier:0,chance:1.0}])} onRemove={i=>set("bottleEffects",(bottleEffects||[]).filter((_,j)=>j!==i))} onUpdate={(i,k,v)=>set("bottleEffects",(bottleEffects||[]).map((e,j)=>j===i?{...e,[k]:v}:e))}/>
        </Section>}
        <Section title="Bottle Ingredients" open={openMap.bottleingredients} onToggle={()=>toggle("bottleingredients")}>
          <TooltipsSection tooltipKeys={bottleTooltipKeys||[]} customTooltipKeys={customTooltipKeys} tooltipSearch={bottleTooltipSearch||""} newTooltipKey={newTooltipKey} newTooltipDisplay={newTooltipDisplay} isCompat={isCompat} compatKey={compatKey} customCompatKeys={customCompatKeys} newCompatKey={newCompatKey} newCompatDisplay={newCompatDisplay} showEffectTooltip={bottleShowEffectTooltip} javaClass="DrinkableItem" effects={bottleEffects||[]} hasFood={bottleHasFood} onSet={(k,v)=>set(k.replace("tooltipKeys","bottleTooltipKeys").replace("tooltipSearch","bottleTooltipSearch"),v)} onChange={updates=>onChange({...item,...updates})}/>
        </Section>
      </>}
      {isFluid&&isCF&&item.createBowl&&<>
        <Section title="Bowl Properties" open={openMap.bowlprops} onToggle={()=>toggle("bowlprops")}>
          <Info>ConsumableItem registered for the bowl form of this fluid. Stack size is always 16.</Info>
          <FoodSection hasFood={item.bowlHasFood} nutrition={item.bowlNutrition} saturation={item.bowlSaturation} fast={item.bowlFast} onToggle={v=>set("bowlHasFood",v)} onNut={v=>set("bowlNutrition",v)} onSat={v=>set("bowlSaturation",v)} onFast={v=>set("bowlFast",v)}/>
        </Section>
        {item.bowlHasFood&&<Section title="Bowl Effects" open={openMap.bowleffects} onToggle={()=>toggle("bowleffects")}>
          <EffectsSection effects={item.bowlEffects||[]} hasFood={item.bowlHasFood} javaClass="ConsumableItem" showEffectTooltip={item.bowlShowEffectTooltip} onShowEffect={v=>set("bowlShowEffectTooltip",v)} onAdd={()=>set("bowlEffects",[...(item.bowlEffects||[]),{effectId:EFFECTS[0].id,duration:1200,amplifier:0,chance:1.0}])} onRemove={i=>set("bowlEffects",(item.bowlEffects||[]).filter((_,j)=>j!==i))} onUpdate={(i,k,v)=>set("bowlEffects",(item.bowlEffects||[]).map((e,j)=>j===i?{...e,[k]:v}:e))}/>
        </Section>}
        <Section title="Bowl Ingredients" open={openMap.bowlingredients} onToggle={()=>toggle("bowlingredients")}>
          <TooltipsSection tooltipKeys={bowlTooltipKeys||[]} customTooltipKeys={customTooltipKeys} tooltipSearch={bowlTooltipSearch||""} newTooltipKey={newTooltipKey} newTooltipDisplay={newTooltipDisplay} isCompat={isCompat} compatKey={compatKey} customCompatKeys={customCompatKeys} newCompatKey={newCompatKey} newCompatDisplay={newCompatDisplay} showEffectTooltip={item.bowlShowEffectTooltip} javaClass="ConsumableItem" effects={item.bowlEffects||[]} hasFood={item.bowlHasFood} onSet={(k,v)=>set(k.replace("tooltipKeys","bowlTooltipKeys").replace("tooltipSearch","bowlTooltipSearch"),v)} onChange={updates=>onChange({...item,...updates})}/>
        </Section>
      </>}
      {!isFluid&&<Section title="Ingredients" open={openMap.tooltips} onToggle={()=>toggle("tooltips")}>
        <TooltipsSection tooltipKeys={tooltipKeys} customTooltipKeys={customTooltipKeys} tooltipSearch={tooltipSearch} newTooltipKey={newTooltipKey} newTooltipDisplay={newTooltipDisplay} isCompat={isCompat} compatKey={compatKey} customCompatKeys={customCompatKeys} newCompatKey={newCompatKey} newCompatDisplay={newCompatDisplay} showEffectTooltip={showEffectTooltip}
                         javaClass={isFluid?"DrinkableItem":isBlock?sliceJavaClass:(["consumable","bowlConsumable"].includes(itemType)?"ConsumableItem":itemType==="bottle"?"DrinkableItem":"Item")}
                         effects={isFluid?(bottleEffects||[]):isBlock?(sliceEffects||[]):effects}
                         hasFood={isFluid?bottleHasFood:isBlock?sliceHasFood:itemTypeHasFood(itemType)}
                         onSet={(k,v)=>set(k,v)} onChange={updates=>onChange({...item,...updates})}/>
      </Section>}
      {isItem&&isCF&&!itemTypeNoDisplay(itemType)&&!isAutoDisplayBlock(id)&&<Section title="Display Block" open={openMap.displayblock} onToggle={()=>toggle("displayblock")}>
        <Tog label="Has display block" value={hasDisplayBlock} onChange={v=>set("hasDisplayBlock",v)}/>
        {hasDisplayBlock&&<>
          {id&&isAutoDisplayBlock(id)&&<Info>"{id}" matches an existing pattern in ModDisplayBlocks — no new code needed. The item will be caught automatically.</Info>}
          {id&&!isAutoDisplayBlock(id)&&<>
            {(displayConfigs||[]).map((c,i)=><div key={i} style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"6px 8px",marginBottom:4}}>
              <div style={{display:"flex",gap:6,alignItems:"flex-end"}}>
                <div style={{flex:1}}><Sel label="Type" value={c.type} onChange={v=>updDispConfig(i,"type",v)} options={DISPLAY_TYPES.map(t=>({v:t,l:t}))} note={{"PLATE":"Flat plate that holds one or more food servings on top.","SMALL_PLATE":"Smaller plate variant — use for snack-sized or half-portion foods.","BOTTLE":"Upright bottle display. Height controls how tall it sits.","BOWL":"Bowl display. Height controls sit height. Optionally shows particles.","SALAD_BOWL":"Larger bowl variant for salads and larger servings.","PLATE_FOOD":"Food item placed directly on a plate surface without the plate rim."}[c.type]}/></div>
                {(c.type==="PLATE"||c.type==="SMALL_PLATE"||c.type==="PLATE_FOOD")&&<div style={{width:72}}><Num label="Max stack" value={c.maxStack||1} onChange={v=>updDispConfig(i,"maxStack",Math.round(v))} min={1} max={64}/></div>}
                {(c.type==="BOTTLE"||c.type==="BOWL"||c.type==="SALAD_BOWL")&&<div style={{width:64}}><Num label="Height" value={c.height||12} onChange={v=>updDispConfig(i,"height",v)} min={1} max={16} step={0.5}/></div>}
                <button onClick={()=>remDispConfig(i)} style={{background:"none",border:"none",color:i===0&&(displayConfigs||[]).length===1?"transparent":S.red,cursor:i===0&&(displayConfigs||[]).length===1?"default":"pointer",fontSize:13,padding:"0 2px",marginBottom:10}} disabled={i===0&&(displayConfigs||[]).length===1}>✕</button>
              </div>
              {(c.type==="BOTTLE"||c.type==="BOWL")&&<Tog label="Has particles" value={c.hasParticles||false} onChange={v=>updDispConfig(i,"hasParticles",v)}/>}
            </div>)}
            <Btn small onClick={addDispConfig}>+ Add display type</Btn>
          </>}
        </>}
      </Section>}
      <Section title="Variations" open={openMap.variations} onToggle={()=>toggle("variations")}>
        <Tog label="Create variants" value={hasVariants} onChange={v=>set("hasVariants",v)}/>
        {hasVariants&&<>
          <Inp label="ID pattern" value={varPattern||("[v]_"+id)} onChange={v=>set("varPattern",v)} placeholder={"[v]_"+id} mono note="[v] is replaced with the variant key."/>
          <Inp label="Display name pattern" value={varNamePat||("[v] "+(displayName||id))} onChange={v=>set("varNamePat",v)} placeholder={"[v] "+(displayName||id)} note="[v] is replaced with the variant display name."/>
          <div style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"7px 9px",marginBottom:8}}>
            <div style={{fontSize:10,color:S.muted,marginBottom:4}}>Variant</div>
            <div style={{display:"flex",gap:4}}>
              <input value={varNewId||""} onChange={e=>set("varNewId",e.target.value)} placeholder="key (e.g. berry)" onKeyDown={e=>e.key==="Enter"&&addVariant()} style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"monospace",outline:"none"}}/>
              <input value={varNewName||""} onChange={e=>set("varNewName",e.target.value)} placeholder="Display Name" onKeyDown={e=>e.key==="Enter"&&addVariant()} style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"inherit",outline:"none"}}/>
              <Btn small onClick={addVariant}>Add</Btn>
            </div>
          </div>
          {(variants||[]).map((v,vi)=>{
            const allAvail=[...BASE_TOOLTIP_KEYS,...(customTooltipKeys||[]).map(c=>c.key)];
            const removeBtn=<button onClick={()=>removeVariant(vi)} style={{background:"none",border:"none",color:S.red,cursor:"pointer",fontSize:12,padding:"0 2px"}}>✕</button>;
            if(isFluid){
              return(<React.Fragment key={vi}>
                {createBottle&&bottleHasFood&&<div style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"6px 8px",marginBottom:4}}>
                  <div style={{display:"flex",justifyContent:"space-between",alignItems:"center",marginBottom:4}}>
                    <div style={{display:"flex",gap:6,alignItems:"center"}}><code style={{fontSize:11,color:"#7dd3fc"}}>{v.id}_bottle</code><span style={{fontSize:9,color:S.muted,border:"1px solid "+S.border,borderRadius:3,padding:"0 4px"}}>bottle</span></div>
                    <div style={{display:"flex",gap:4,alignItems:"center"}}><span style={{fontSize:10,color:S.muted}}>{v.displayName}</span>{removeBtn}</div>
                  </div>
                  <div style={{display:"flex",gap:8,marginBottom:4}}><div style={{flex:1}}><Num label="Nutrition" value={v.nutrition} onChange={val=>updVariant(vi,"nutrition",val)} min={1} max={20}/></div><div style={{flex:1}}><Num label="Saturation" value={v.saturation} onChange={val=>updVariant(vi,"saturation",val)} min={0} max={3} step={0.1}/></div></div>
                  <EffectsSection effects={v.effects||[]} hasFood={bottleHasFood} javaClass="DrinkableItem" onAdd={()=>addVarEffect(vi)} onRemove={ei=>remVarEffect(vi,ei)} onUpdate={(ei,k,val)=>updVarEffect(vi,ei,k,val)}/>
                  <IngredientsPicker vKeys={v.bottleTooltipKeys||[]} vSearch={v.bottleTooltipSearch||""} setVKeys={keys=>updVariant(vi,"bottleTooltipKeys",keys)} setSearch={val=>updVariant(vi,"bottleTooltipSearch",val)} allAvail={allAvail}/>
                </div>}
                {item.createBowl&&item.bowlHasFood&&<div style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"6px 8px",marginBottom:4}}>
                  <div style={{display:"flex",justifyContent:"space-between",alignItems:"center",marginBottom:4}}>
                    <div style={{display:"flex",gap:6,alignItems:"center"}}><code style={{fontSize:11,color:"#7dd3fc"}}>{v.id}_bowl</code><span style={{fontSize:9,color:S.muted,border:"1px solid "+S.border,borderRadius:3,padding:"0 4px"}}>bowl</span></div>
                    <div style={{display:"flex",gap:4,alignItems:"center"}}><span style={{fontSize:10,color:S.muted}}>{v.displayName}</span>{removeBtn}</div>
                  </div>
                  <div style={{display:"flex",gap:8,marginBottom:4}}><div style={{flex:1}}><Num label="Nutrition" value={v.bowlNutrition!=null?v.bowlNutrition:item.bowlNutrition} onChange={val=>updVariant(vi,"bowlNutrition",val)} min={1} max={20}/></div><div style={{flex:1}}><Num label="Saturation" value={v.bowlSaturation!=null?v.bowlSaturation:item.bowlSaturation} onChange={val=>updVariant(vi,"bowlSaturation",val)} min={0} max={3} step={0.1}/></div></div>
                  <EffectsSection effects={v.bowlEffects||[]} hasFood={item.bowlHasFood} javaClass="ConsumableItem" onAdd={()=>addVarBowlEffect(vi)} onRemove={ei=>remVarBowlEffect(vi,ei)} onUpdate={(ei,k,val)=>updVarBowlEffect(vi,ei,k,val)}/>
                  <IngredientsPicker vKeys={v.bowlTooltipKeys||[]} vSearch={v.bowlTooltipSearch||""} setVKeys={keys=>updVariant(vi,"bowlTooltipKeys",keys)} setSearch={val=>updVariant(vi,"bowlTooltipSearch",val)} allAvail={allAvail}/>
                </div>}
              </React.Fragment>);
            }
            return(<div key={vi} style={{background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"6px 8px",marginBottom:4}}>
              <div style={{display:"flex",justifyContent:"space-between",alignItems:"center",marginBottom:4}}>
                <code style={{fontSize:11,color:"#7dd3fc"}}>{v.id}</code>
                <div style={{display:"flex",gap:4,alignItems:"center"}}><span style={{fontSize:10,color:S.muted}}>{v.displayName}</span>{removeBtn}</div>
              </div>
              {(isItem&&itemTypeHasFood(itemType))||(isBlock&&sliceHasFood)?<div style={{display:"flex",gap:8,marginBottom:4}}><div style={{flex:1}}><Num label="Nutrition" value={v.nutrition} onChange={val=>updVariant(vi,"nutrition",val)} min={1} max={20}/></div><div style={{flex:1}}><Num label="Saturation" value={v.saturation} onChange={val=>updVariant(vi,"saturation",val)} min={0} max={3} step={0.1}/></div></div>:null}
              <EffectsSection effects={v.effects||[]} hasFood={(isItem&&itemTypeHasFood(itemType))||(isBlock&&sliceHasFood)||false} javaClass={isBlock?sliceJavaClass:(["consumable","bowlConsumable"].includes(itemType)?"ConsumableItem":itemType==="bottle"?"DrinkableItem":"Item")} onAdd={()=>addVarEffect(vi)} onRemove={ei=>remVarEffect(vi,ei)} onUpdate={(ei,k,val)=>updVarEffect(vi,ei,k,val)}/>
              <IngredientsPicker vKeys={v.tooltipKeys||[]} vSearch={v.tooltipSearch||""} setVKeys={keys=>updVariant(vi,"tooltipKeys",keys)} setSearch={val=>updVariant(vi,"tooltipSearch",val)} allAvail={allAvail}/>
            </div>);
          })}
        </>}
      </Section>
      <div style={{marginTop:8,display:"flex",gap:8,alignItems:"center"}}>
        <Btn small onClick={onSave} disabled={!item.id} title="Save current item & recipes, then clear inputs">Save</Btn>
        <Btn color={S.darkred} small onClick={onReset} title="Reset everything including saved files">Reset</Btn>

      </div>
    </div>
    <div style={{overflowY:"auto",paddingLeft:4}}>
      {(()=>{const displayFiles=allItemFiles||files;const displayCount=Object.keys(displayFiles).filter(k=>k!=="_autoSet"&&k!=="_variantSet").length;return(<Section title={"Files ("+displayCount+")"} open={openMap.files} onToggle={()=>toggle("files")}>
        {displayCount===0&&<div style={{color:S.muted,fontSize:12}}>Enter an ID to generate files.</div>}
        {Object.entries(displayFiles).filter(([k])=>k!=="_autoSet"&&k!=="_variantSet").sort(([a],[b])=>a.localeCompare(b)).map(([path,content])=><CodeFile key={path} label={path} content={content} downloadName={path.split("/").pop()} auto={autoSet.has(path)} variant={variantSet.has(path)&&!autoSet.has(path)}/>)}
      </Section>);})()}
    </div>
  </div>);
}

const RECIPES_DEFAULTS={recipes:true,files:true};
const BLANK_RECIPES_STATE={recs:[]};
function RecipeCard({rec,idx,onChange,onRemove,onCopy}){
  const rt=RECIPE_TYPES.find(r=>r.id===rec.recipeType);
  const isVanilla=rec.recipeType.startsWith("minecraft:");const isFD=rec.recipeType.startsWith("farmersdelight:");
  const isCreate=CREATE_REC_IDS.has(rec.recipeType);
  const isMixComp=["create:mixing","create:mixing_heated","create:compacting","create:compacting_heated","create:filling","create:emptying"].includes(rec.recipeType);
  const isShaped=rec.recipeType==="minecraft:crafting_shaped";
  const upd=(k,v)=>onChange({...rec,[k]:v});const updInp=(i,v)=>onChange({...rec,inputs:rec.inputs.map((x,j)=>j===i?v:x)});
  const json=buildRecipeJson(rec),path=recipeFilePath(rec);
  return (<div style={{background:S.panel,border:"1px solid "+S.border,borderRadius:7,padding:12,marginBottom:10}}>
    <div style={{display:"flex",justifyContent:"space-between",alignItems:"center",marginBottom:8}}>
      <span style={{background:S.accent,color:S.text,borderRadius:4,padding:"1px 8px",fontSize:11,fontWeight:700}}>#{idx+1}</span>
      <div style={{display:"flex",gap:4}}><Btn small color={S.faint} onClick={onCopy}>Copy</Btn><Btn small color={S.darkred} onClick={onRemove}>Remove</Btn></div>
    </div>
    <Sel value={rec.recipeType} onChange={v=>upd("recipeType",v)} options={RECIPE_TYPES.map(r=>({v:r.id,l:r.label}))} note={RECIPE_TYPES.find(r=>r.id===rec.recipeType)?.desc}/>
    {!isShaped&&<>
      <div style={{fontSize:10,color:S.muted,marginBottom:4}}>Inputs</div>
      {rec.inputs.map((inp,i)=>(
          <div key={i} style={{display:"flex",gap:4,marginBottom:4}}>
            <select value={inp.type} onChange={e=>updInp(i,{...inp,type:e.target.value})} style={{background:S.code,border:"1px solid "+S.border,color:S.muted,borderRadius:4,padding:"4px 5px",fontSize:11,fontFamily:"inherit",outline:"none"}}>
              <option value="tag">tag c:</option><option value="item">item</option>
              {isCreate&&<option value="fluid_tag">fluid|mB</option>}
            </select>
            <input value={inp.value} onChange={e=>updInp(i,{...inp,value:e.target.value})} placeholder={inp.type==="fluid_tag"?"apple_custard|250":inp.type==="item"?"minecraft:glass_bottle":"tag name"} style={{flex:1,background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"4px 7px",fontSize:11,fontFamily:"monospace",outline:"none"}}/>
            {rec.inputs.length>1&&<button onClick={()=>onChange({...rec,inputs:rec.inputs.filter((_,j)=>j!==i)})} style={{background:"none",border:"none",color:S.red,cursor:"pointer",fontSize:12,padding:"0 2px"}}>✕</button>}
          </div>
      ))}
      <button onClick={()=>onChange({...rec,inputs:[...rec.inputs,{type:"tag",value:""}]})} style={{background:"none",border:"1px dashed "+S.border,color:S.muted,borderRadius:4,padding:"2px 8px",fontSize:10,cursor:"pointer",marginBottom:8}}>+ input</button>
    </>}
    {!isShaped&&<>
      {isMixComp&&rec.isFluidOutput&&<div style={{display:"flex",gap:8}}>
        <div style={{flex:3}}><Inp label="Output ID" value={rec.outputId} onChange={v=>upd("outputId",v)} placeholder="my_fluid" mono/></div>
        <div style={{flex:1}}><Num label="mB" value={rec.fluidAmount} onChange={v=>upd("fluidAmount",v)} min={1} max={4000}/></div>
      </div>}
      {(!isMixComp||!rec.isFluidOutput)&&<div style={{display:"flex",gap:8}}>
        <div style={{flex:3}}><Inp label="Output ID" value={rec.outputId} onChange={v=>upd("outputId",v)} placeholder="beef_bun_bacon" mono/></div>
        <div style={{flex:1}}><Num label="Count" value={rec.outputCount} onChange={v=>upd("outputCount",v)} min={1} max={64}/></div>
      </div>}
      {isMixComp&&<Tog label="Fluid output" value={rec.isFluidOutput} onChange={v=>upd("isFluidOutput",v)}/>}
      {rec.recipeType==="create:item_application"&&<Inp label="Byproduct ID (optional)" value={rec.byproductId||""} onChange={v=>upd("byproductId",v)} placeholder="piping_bag" mono note="Second item returned to the player (e.g. the applicator). Leave blank for none."/>}
      {(isFD||(isVanilla&&!rec.recipeType.includes("crafting")))&&<div style={{display:"flex",gap:8}}><div style={{flex:1}}><Num label="XP" value={rec.experience} onChange={v=>upd("experience",v)} min={0} max={10} step={0.05}/></div>{isVanilla&&!rec.recipeType.includes("crafting")&&<div style={{flex:1}}><Num label="Cook ticks" value={rec.cookingtime} onChange={v=>upd("cookingtime",v)} min={20} max={600}/></div>}</div>}
    </>}
    {isShaped&&<>
      <div style={{fontSize:10,color:S.muted,marginBottom:4}}>Pattern — click cells to cycle through keys</div>
      <div style={{display:"inline-grid",gridTemplateColumns:"repeat(3,36px)",gap:3,marginBottom:8}}>
        {(rec.shapedPattern||["   ","   ","   "]).map((row,ri)=>[...Array(3)].map((_,ci)=>{
          const ch=(row[ci]||" ").toUpperCase();
          const usedLetters=[...new Set((rec.shapedKey||[]).map(k=>k.letter).filter(Boolean))];
          const cycle=[" ",...usedLetters];
          const next=()=>{const idx=cycle.indexOf(ch);const nc=cycle[(idx+1)%cycle.length];const rows=[...(rec.shapedPattern||["   ","   ","   "])];const r=[...rows[ri].padEnd(3)];r[ci]=nc;rows[ri]=r.join("");upd("shapedPattern",rows);};
          return(<button key={ci} onClick={next} style={{width:36,height:36,background:ch===" "?S.code:S.accent,border:"1px solid "+(ch===" "?S.border:S.accent),color:S.text,borderRadius:4,fontSize:14,fontFamily:"monospace",fontWeight:700,cursor:"pointer",display:"flex",alignItems:"center",justifyContent:"center"}}>{ch===" "?"·":ch}</button>);
        }))}
      </div>
      <div style={{fontSize:10,color:S.muted,marginBottom:4}}>Keys (letter → ingredient)</div>
      {(rec.shapedKey||[{letter:"X",inputType:"tag",input:""}]).map((k,ki)=><div key={ki} style={{display:"flex",gap:4,marginBottom:4,alignItems:"center"}}>
        <input value={k.letter} maxLength={1} onChange={e=>{const ltr=(e.target.value.slice(-1)||"A").toUpperCase();upd("shapedKey",(rec.shapedKey||[]).map((x,j)=>j===ki?{...x,letter:ltr}:x));}} style={{width:36,height:36,background:S.accent,border:"1px solid "+S.accent,color:S.text,borderRadius:4,fontSize:14,fontFamily:"monospace",fontWeight:700,textAlign:"center",outline:"none",flexShrink:0,boxSizing:"border-box"}}/>
        <select value={k.inputType} onChange={e=>upd("shapedKey",(rec.shapedKey||[]).map((x,j)=>j===ki?{...x,inputType:e.target.value}:x))} style={{height:36,background:S.code,border:"1px solid "+S.border,color:S.muted,borderRadius:4,padding:"0 5px",fontSize:11,fontFamily:"inherit",outline:"none"}}><option value="tag">tag c:</option><option value="item">item</option></select>
        <input value={k.input} onChange={e=>upd("shapedKey",(rec.shapedKey||[]).map((x,j)=>j===ki?{...x,input:e.target.value}:x))} placeholder="tag or item id" style={{flex:1,height:36,boxSizing:"border-box",background:S.code,border:"1px solid "+S.border,color:S.text,borderRadius:4,padding:"0 7px",fontSize:11,fontFamily:"monospace",outline:"none"}}/>
        {(rec.shapedKey||[]).length>1&&<button onClick={()=>upd("shapedKey",(rec.shapedKey||[]).filter((_,j)=>j!==ki))} style={{background:"none",border:"none",color:S.red,cursor:"pointer",fontSize:12,padding:"0 2px"}}>✕</button>}
      </div>)}
      <button onClick={()=>{const next=String.fromCharCode(65+(rec.shapedKey||[]).length);upd("shapedKey",[...(rec.shapedKey||[]),{letter:next,inputType:"tag",input:""}]);}} style={{background:"none",border:"1px dashed "+S.border,color:S.muted,borderRadius:4,padding:"2px 8px",fontSize:10,cursor:"pointer",marginBottom:8}}>+ key</button>
      <div style={{display:"flex",gap:8}}>
        <div style={{flex:3}}><Inp label="Output ID" value={rec.outputId} onChange={v=>upd("outputId",v)} placeholder="beef_bun_bacon" mono/></div>
        <div style={{flex:1}}><Num label="Count" value={rec.outputCount} onChange={v=>upd("outputCount",v)} min={1} max={64}/></div>
      </div>
    </>}
    <Inp label="Filename suffix (optional)" value={rec.altSuffix||""} onChange={v=>upd("altSuffix",v)} placeholder="alt" mono/>
    {path&&json&&<CodeFile label={path} content={json} downloadName={(rec.outputId.includes(":")?rec.outputId.split(":")[1]:rec.outputId)+(rt?.suffix||"")+".json"}/>}
  </div>);
}
function RecipesPanel({recipeState,setRecipeState,item,sections,setSections,autoRecipeFiles,onSave,onReset,hasSaved,allRecipeFiles}){
  const{recs}=recipeState;const setRecs=v=>setRecipeState(s=>({...s,recs:v}));
  const prefill=item?.id||"";
  const addRec=()=>setRecs([...recs,{...BLANK_REC(),outputId:prefill}]);
  const updRec=(uid,v)=>setRecs(recs.map(r=>r.uid===uid?v:r));
  const remRec=uid=>setRecs(recs.filter(r=>r.uid!==uid));
  const copyRec=uid=>{const r=recs.find(r=>r.uid===uid);setRecs([...recs,{...r,uid:Math.random().toString(36).slice(2)}]);};
  const recFiles=Object.fromEntries(recs.map(r=>{const p=recipeFilePath(r);const j=buildRecipeJson(r);return p&&j?[p,j]:null}).filter(Boolean));
  const autoFiles=autoRecipeFiles||{};
  const totalCount=Object.keys(recFiles).length+Object.keys(autoFiles).length;
  const{openMap,toggle}=usePersistedSections("recipes",RECIPES_DEFAULTS,sections,setSections);
  return (<div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:16,height:"100%",minHeight:0}}>
    <div style={{overflowY:"auto",paddingRight:4}}>
      <Section title="Recipes" open={openMap.recipes} onToggle={()=>toggle("recipes")}>
        {recs.map((r,i)=><RecipeCard key={r.uid} rec={r} idx={i} onChange={v=>updRec(r.uid,v)} onRemove={()=>remRec(r.uid)} onCopy={()=>copyRec(r.uid)}/>)}
        <div style={{marginTop:4,display:"flex",justifyContent:"flex-end"}}><Btn small onClick={addRec}>+ Add</Btn></div>
      </Section>
      <div style={{display:"flex",justifyContent:"space-between",alignItems:"center",marginTop:8}}><div style={{display:"flex",gap:8}}><Btn small onClick={onSave} disabled={!item.id} title="Save current item & recipes, then clear inputs">Save</Btn><Btn color={S.darkred} small onClick={onReset} title="Reset everything including saved files">Reset</Btn></div></div>
    </div>
    <div style={{overflowY:"auto",paddingLeft:4}}>
      {(()=>{const displayRecFiles=allRecipeFiles||{...recFiles,...autoFiles};const displayCount=Object.keys(displayRecFiles).length;return(<Section title={"Files ("+displayCount+")"} open={openMap.files} onToggle={()=>toggle("files")}>
        {displayCount===0&&<div style={{color:S.muted,fontSize:12}}>No recipe files yet.</div>}
        {Object.entries(displayRecFiles).sort(([a],[b])=>a.localeCompare(b)).map(([path,content])=><CodeFile key={path} label={path} content={content} downloadName={path.split("/").pop()} auto={autoFiles.hasOwnProperty(path)}/>)}
      </Section>);})()}
    </div>
  </div>);
}

function GroupedFiles({grp,items,checked,toggleCheck,keyPrefix}){
  const pfx=keyPrefix||"gen_";
  const[open,setOpen]=useState(true);
  return(<div style={{marginBottom:4,overflow:"hidden",minWidth:0}}>
    <div onClick={()=>setOpen(!open)} style={{display:"flex",alignItems:"center",gap:4,cursor:"pointer",userSelect:"none",marginBottom:2,overflow:"hidden",minWidth:0}}>
      <span style={{fontSize:8,color:S.muted,display:"inline-block",flexShrink:0,transform:open?"rotate(90deg)":"rotate(0deg)",transition:"transform .12s"}}>▶</span>
      <span title={grp+"/"} style={{fontSize:9,color:S.muted,fontFamily:"monospace",whiteSpace:"nowrap"}}>{grp}/</span>
    </div>
    {open&&items.map(({p,i})=>{const key=pfx+i;const done=checked[key];const fname=p.split("/").pop();return(<div key={key} onClick={()=>toggleCheck(key)} style={{display:"flex",alignItems:"center",gap:5,fontSize:10,color:done?S.muted:S.text,marginBottom:2,cursor:"pointer",textDecoration:done?"line-through":"none",paddingLeft:12,overflow:"hidden",minWidth:0}}><span style={{width:12,height:12,minWidth:12,flexShrink:0,border:"1px solid "+(done?S.green:S.border),borderRadius:2,background:done?S.green:"none",display:"flex",alignItems:"center",justifyContent:"center",fontSize:8,color:"#fff"}}>{done?"✓":""}</span><span title={p} style={{fontFamily:"monospace",whiteSpace:"nowrap"}}>{fname.length>30?fname.slice(0,29)+"…":fname}</span></div>);})}
  </div>);
}
function GroupedCodeFiles({grp,entries,autoSet}){
  const[open,setOpen]=useState(true);
  return(<div style={{marginBottom:4}}>
    <div onClick={()=>setOpen(!open)} style={{display:"flex",alignItems:"center",gap:4,cursor:"pointer",userSelect:"none",marginBottom:2}}>
      <span style={{fontSize:8,color:S.muted,display:"inline-block",transform:open?"rotate(90deg)":"rotate(0deg)",transition:"transform .12s"}}>▶</span>
      <span style={{fontSize:9,color:S.muted,fontFamily:"monospace"}}>{grp}/</span>
    </div>
    {open&&entries.map(([path,content])=><CodeFile key={path} label={path.split("/").pop()} content={content} downloadName={path.split("/").pop()} auto={autoSet&&autoSet.has(path)}/>)}
  </div>);
}

function RegistrationGroup({grp,items,checked,toggleCheck}){
  const[open,setOpen]=useState(true);
  return(<div style={{marginBottom:4}}>
    <div onClick={()=>setOpen(!open)} style={{display:"flex",alignItems:"center",gap:4,cursor:"pointer",userSelect:"none",marginBottom:2}}>
      <span style={{fontSize:8,color:S.muted,display:"inline-block",transform:open?"rotate(90deg)":"rotate(0deg)",transition:"transform .12s"}}>▶</span>
      <span style={{fontSize:9,color:S.muted,fontFamily:"monospace"}}>{grp}</span>
    </div>
    {open&&items.map(({p,i})=>{const key="file_"+i;const done=checked[key];const desc=p.includes(" —")?p.split(" —").slice(1).join(" —").trim():p;return(<div key={key} onClick={()=>toggleCheck(key)} style={{display:"flex",alignItems:"flex-start",gap:5,fontSize:10,color:done?S.muted:S.text,marginBottom:2,cursor:"pointer",textDecoration:done?"line-through":"none",paddingLeft:12,overflow:"hidden",minWidth:0}}><span style={{width:12,height:12,minWidth:12,border:"1px solid "+(done?S.green:S.border),borderRadius:2,background:done?S.green:"none",display:"flex",alignItems:"center",justifyContent:"center",fontSize:8,color:"#fff",marginTop:1}}>{done?"✓":""}</span><span title={p} style={{fontFamily:"monospace",whiteSpace:"nowrap"}}>{desc.length>30?desc.slice(0,29)+"…":desc}</span></div>);})}
  </div>);
}

const IE_DEFAULTS={exp:true,checklist:true};
function ImportExportPanel({itemFiles,recipeFiles,autoRecipeFiles,sections,setSections,item,recipeState,savedItemSnapshots,savedItemFiles,savedRecipeFiles2,onBatchImport}){
  const[checked,setChecked]=useState({});
  const toggleCheck=k=>setChecked(s=>({...s,[k]:!s[k]}));

  const[specLog,setSpecLog]=useState([]);
  const[specError,setSpecError]=useState("");
  const handleSpecFile=async e=>{
    const files=Array.from(e.target.files||[]);if(!files.length)return;
    setSpecLog([]);setSpecError("");
    try{
      await importFromFiles(files,{
        onImportRecipe:()=>{},onImportItem:()=>{},setLog:setSpecLog,
        onBatchImport:(iFiles,rFiles,snaps)=>{if(onBatchImport)onBatchImport(iFiles,rFiles,snaps);}
      });
    }catch(err){setSpecError(String(err));}
    e.target.value="";
  };
  const{openMap,toggle}=usePersistedSections("ie",IE_DEFAULTS,sections,setSections);
  const{ns,localId:lid,isCF}=parseNs(item.id||"");const id=lid;
  const isFluid=item.registrationType==="fluid";const isBlock=item.registrationType==="block";const isItemT=item.registrationType==="item";
  const effSlice=item.sliceId||(id?id+"_slice":"");const isRaw=item.blockType==="block_raw_pie"||item.blockType==="block_raw_pizza";
  const allIN=Object.keys(itemFiles).filter(k=>k!=="_autoSet"&&k!=="_variantSet").length;
  const allRN=Object.keys(recipeFiles).length;
  const clTextures=[];const clFiles=[];const clGenerated=[];
  const addItemChecklist=(snap)=>{
    const{ns:sNs,localId:sId,isCF:sCF}=parseNs(snap.id||"");
    if(!sId)return;
    const sIsItem=snap.registrationType==="item",sIsBlock=snap.registrationType==="block",sIsFluid=snap.registrationType==="fluid";
    const sEffSlice=snap.sliceId||(sId+"_slice");const sIsRaw=snap.blockType==="block_raw_pie"||snap.blockType==="block_raw_pizza";
    if(sIsItem&&sCF){
      clTextures.push("textures/item/"+sId+".png");
      clFiles.push("ModItems.java — register item: "+sId);
      if(snap.isCompat)clFiles.push("ConfigDefaults.java — add "+sId+" to HIDE_ITEMS_DEFAULT");
      if(!snap.hasDisplayBlock&&!isAutoDisplayBlock(sId)&&!itemTypeNoDisplay(snap.itemType))clFiles.push("ModDisplayBlocks.java — add "+sId+" to EXCLUDED_ITEMS");
    }
    if(sIsBlock&&sCF){
      clTextures.push("textures/block/"+sId+"_top.png");
      clTextures.push("textures/block/"+sId+"_side.png");
      if(snap.blockType==="block_cake")clTextures.push("textures/block/"+sId+"_inner.png");
      if(!sIsRaw)clTextures.push("textures/item/"+sEffSlice+".png");
      clFiles.push("ModBlocks.java — register block: "+sId);
      if(!sIsRaw)clFiles.push("ModItems.java — register slice: "+sEffSlice);
    }
    if(sIsFluid&&sCF){
      clTextures.push("textures/fluid/"+sId+"_still.png");
      clTextures.push("textures/fluid/"+sId+"_flow.png");
      clTextures.push("textures/item/"+sId+"_bucket.png");
      if(snap.createBottle)clTextures.push("textures/item/"+sId+"_bottle.png");
      if(snap.createBowl)clTextures.push("textures/item/"+sId+"_bowl.png");
      clFiles.push("ModFluids.java — register fluid: "+sId);
      if(snap.createBottle)clFiles.push("ModItems.java — register bottle: "+sId+"_bottle");
      if(snap.createBowl)clFiles.push("ModItems.java — register bowl: "+sId+"_bowl");
    }
    if(!sCF)clFiles.push("Non-createfood namespace ("+sNs+") — only lang/recipe files generated");
    if(snap.hasVariants&&(snap.variants||[]).length>0){
      const sIsFluidReg=snap.registrationType==="fluid";const sIsBlockReg=snap.registrationType==="block";const sIsRawV=snap.blockType==="block_raw_pie"||snap.blockType==="block_raw_pizza";
      (snap.variants||[]).forEach(v=>{
        const{localId:vid}=parseNs(v.id);
        if(sIsFluidReg){
          clTextures.push("textures/fluid/"+vid+"_still.png");clTextures.push("textures/fluid/"+vid+"_flow.png");clTextures.push("textures/item/"+vid+"_bucket.png");
          if(snap.createBottle)clTextures.push("textures/item/"+vid+"_bottle.png");
          if(snap.createBowl)clTextures.push("textures/item/"+vid+"_bowl.png");
        } else {
          clTextures.push("textures/item/"+vid+".png");
          if(sIsBlockReg&&!sIsRawV)clTextures.push("textures/item/"+vid+"_slice.png");
        }
      });
      clFiles.push("ModItems.java — register variants for: "+sId);
    }
  };
  (savedItemSnapshots||[]).forEach(snap=>addItemChecklist(snap));
  if(id)addItemChecklist(item);
  const jsonFiles=Object.keys(itemFiles).filter(k=>k!=="_autoSet"&&k!=="_variantSet"&&!k.endsWith(".java"));
  jsonFiles.forEach(k=>clGenerated.push(k));
  Object.keys(savedRecipeFiles2||{}).forEach(k=>{if(!clGenerated.includes(k))clGenerated.push(k);});
  const autoKeys=Object.keys(autoRecipeFiles||{});
  autoKeys.forEach(k=>{if(!clGenerated.includes(k))clGenerated.push(k);});
  const recN=recipeState.recs.length;
  if(recN>0){recipeState.recs.forEach(r=>{const p=recipeFilePath(r);if(p&&!clGenerated.includes(p))clGenerated.push(p);});}
  const hasAny=clTextures.length>0||clFiles.length>0||clGenerated.length>0;
  return (<div style={{display:"flex",flexDirection:"column",height:"100%",minHeight:0,overflowY:"auto"}}>
    <div style={{display:"grid",gridTemplateColumns:"1fr 1fr",gap:16}}>
      <div style={{paddingRight:4}}>
        <Section title="Checklist" open={openMap.checklist} onToggle={()=>toggle("checklist")}>
          <div style={{display:"grid",gridTemplateColumns:"1fr 1fr 1fr",gap:12,alignItems:"start"}}>
            <div style={{overflow:"hidden",minWidth:0}}>
              <div style={{fontSize:10,fontWeight:700,color:S.text,letterSpacing:"0.08em",textTransform:"uppercase",marginBottom:4}}>Generated JSONs</div>
              {clGenerated.length===0&&<div style={{fontSize:11,color:S.muted}}>—</div>}
              {(()=>{
                const groups={};
                clGenerated.forEach((p,i)=>{const parts=p.split("/");const grp=parts.length>3?parts.slice(0,3).join("/"):parts.slice(0,2).join("/");if(!groups[grp])groups[grp]=[];groups[grp].push({p,i});});
                return Object.entries(groups).map(([grp,items])=>(<GroupedFiles key={grp} grp={grp} items={items} checked={checked} toggleCheck={toggleCheck} keyPrefix="gen_"/>));
              })()}
            </div>
            <div style={{overflow:"hidden",minWidth:0}}>
              <div style={{fontSize:10,fontWeight:700,color:S.text,letterSpacing:"0.08em",textTransform:"uppercase",marginBottom:4}}>Required textures</div>
              {clTextures.length===0&&<div style={{fontSize:11,color:S.muted}}>—</div>}
              {(()=>{
                const groups={};
                clTextures.forEach((t,i)=>{const parts=t.split("/");const grp=parts.length>1?parts.slice(0,parts.length-1).join("/"):"other";if(!groups[grp])groups[grp]=[];groups[grp].push({t,i});});
                return Object.entries(groups).map(([grp,items])=>(<GroupedFiles key={grp} grp={grp} items={items.map(({t,i})=>({p:t,i}))} checked={checked} toggleCheck={toggleCheck} keyPrefix="tex_"/>));
              })()}
            </div>
            <div style={{overflow:"hidden",minWidth:0}}>
              <div style={{fontSize:10,fontWeight:700,color:S.text,letterSpacing:"0.08em",textTransform:"uppercase",marginBottom:4}}>Registration edits</div>
              {clFiles.length===0&&<div style={{fontSize:11,color:S.muted}}>—</div>}
              {(()=>{
                const groups={};
                clFiles.forEach((c,i)=>{const grp=c.split(" —")[0].trim();if(!groups[grp])groups[grp]=[];groups[grp].push({p:c,i});});
                return Object.entries(groups).map(([grp,items])=>(<RegistrationGroup key={grp} grp={grp} items={items} checked={checked} toggleCheck={toggleCheck}/>));
              })()}
            </div>
          </div>
        </Section>
      </div>
      <div style={{paddingLeft:4}}>
        <Section title={"Import"} open={openMap.exp} onToggle={()=>toggle("exp")}>
          <div style={{marginBottom:8}}>
            <div style={{fontSize:11,color:S.muted,marginBottom:6}}>Upload a <code style={{fontFamily:"monospace",color:"#7dd3fc"}}>.json</code> file to batch-generate all files.</div>
            <label style={{display:"inline-block",background:S.accent,color:S.text,border:"none",borderRadius:5,padding:"6px 14px",fontSize:12,cursor:"pointer",fontFamily:"inherit"}}>
              ⬆ Import .json
              <input type="file" accept=".json,.zip" multiple onChange={handleSpecFile} style={{display:"none"}}/>
            </label>
          </div>
          {specError&&<div style={{background:"#1c0000",border:"1px solid "+S.red,borderRadius:5,padding:"5px 9px",fontSize:11,color:"#f87171",marginBottom:6}}>{specError}</div>}
          {specLog.length>0&&<div style={{background:S.code,borderRadius:5,padding:"8px 10px",fontSize:11,fontFamily:"monospace",color:"#7dd3fc",maxHeight:120,overflowY:"auto"}}>{specLog.map((l,i)=><div key={i}>{l}</div>)}</div>}
        </Section>
        <Section title={"Export ("+(allIN+allRN)+")"} open={openMap.exp} onToggle={()=>toggle("exp")}>
          <div style={{display:"flex",flexDirection:"column",gap:8}}>
            {[["Item",allIN,itemFiles,"item_files.zip"],["Recipes",allRN,recipeFiles,"recipe_files.zip"]].filter(([,n])=>n>0).map(([label,n,fls,fn])=>(
                <div key={fn} style={{display:"flex",justifyContent:"space-between",alignItems:"center",background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"8px 10px"}}>
                  <span style={{fontSize:12,color:S.text}}>{label} <span style={{color:S.muted}}>({n})</span></span>
                  <Btn small onClick={()=>downloadZip(fls,fn)}>⬇ Download</Btn>
                </div>
            ))}
            {hasAny&&<div style={{display:"flex",justifyContent:"space-between",alignItems:"center",background:S.faint,border:"1px solid "+S.border,borderRadius:5,padding:"8px 10px"}}>
              {(()=>{
                const uncheckedTex=clTextures.filter((_,i)=>!checked["tex_"+i]);
                const uncheckedGen=clGenerated.filter((_,i)=>!checked["gen_"+i]);
                const uncheckedFiles=clFiles.filter((_,i)=>!checked["file_"+i]);
                const total=uncheckedTex.length+uncheckedGen.length+uncheckedFiles.length;
                const allDone=total===0&&(clTextures.length+clGenerated.length+clFiles.length)>0;
                return(<>
                  <div style={{display:"flex",flexDirection:"column",gap:1}}>
                    <span style={{fontSize:12,color:S.text}}>Checklist.md {allDone&&<span style={{fontSize:10,color:"#4ade80"}}>✓ all done</span>}</span>
                    <span style={{fontSize:10,color:S.muted}}>{total} item{total!==1?"s":""} remaining</span>
                  </div>
                  <Btn small onClick={()=>{
                    const lines=["# Checklist\n"];
                    if(uncheckedTex.length){lines.push("## Required textures");uncheckedTex.forEach(c=>lines.push("- [ ] "+c));lines.push("");}
                    if(uncheckedGen.length){lines.push("## Generated JSONs");uncheckedGen.forEach(c=>lines.push("- [ ] "+c));lines.push("");}
                    if(uncheckedFiles.length){lines.push("## Registration edits");uncheckedFiles.forEach(c=>lines.push("- [ ] "+c));lines.push("");}
                    dlFile("CHECKLIST.md",lines.join("\n"));
                  }}>⬇ Download</Btn>
                </>);
              })()}
            </div>}
            {allIN+allRN===0
                ?<div style={{color:S.muted,fontSize:12}}>No files generated yet.</div>
                :<div style={{display:"flex",justifyContent:"space-between",alignItems:"center",background:"#0f1e1a",border:"1px solid #16a34a44",borderRadius:5,padding:"8px 10px"}}>
                  <span style={{fontSize:12,color:S.text}}>All Files</span>
                  <Btn small color="#16a34a" onClick={()=>{
                    const allFls={...itemFiles,...recipeFiles};
                    const uTex=clTextures.filter((_,i)=>!checked["tex_"+i]);
                    const uGen=clGenerated.filter((_,i)=>!checked["gen_"+i]);
                    const uFiles=clFiles.filter((_,i)=>!checked["file_"+i]);
                    const total=uTex.length+uGen.length+uFiles.length;
                    if(total>0){const lines=["# Checklist\n"];if(uTex.length){lines.push("## Required textures");uTex.forEach(c=>lines.push("- [ ] "+c));lines.push("");}if(uGen.length){lines.push("## Generated JSONs");uGen.forEach(c=>lines.push("- [ ] "+c));lines.push("");}if(uFiles.length){lines.push("## Registration edits");uFiles.forEach(c=>lines.push("- [ ] "+c));lines.push("");}allFls["CHECKLIST.md"]=lines.join("\n");}
                    downloadZip(allFls,"all_files.zip");
                  }}>⬇ Download</Btn>
                </div>
            }
          </div>
        </Section>
      </div>
    </div>
  </div>);
}

function Badge({children,color,bg,border}){
  return <span style={{display:"inline-flex",alignItems:"center",background:bg||"transparent",border:"1px solid "+(border||S.border),color:color||S.muted,borderRadius:4,padding:"1px 7px",fontSize:10,fontWeight:500,whiteSpace:"nowrap"}}>{children}</span>;
}
function TierBadge({tier}){
  if(!tier)return null;
  return <Badge bg="#1a1200" border="#a16207" color="#fbbf24">Tier {tier.tier} — {tier.label}</Badge>;
}
function FoodBadges({nutrition,saturation}){
  return <>
    <Badge bg="#0f1e35" border={S.accent} color="#93c5fd">Nutrition {nutrition}</Badge>
    <Badge bg="#0f1e35" border={S.accent} color="#93c5fd">Saturation {saturation}</Badge>
  </>;
}
function EffectsBadge({effects}){
  if(!effects||!effects.length)return null;
  return <>{effects.map((e,i)=>{const lbl=EFFECTS.find(x=>x.id===e.effectId)?.label||e.effectId;return <Badge key={i} color="#f87171" border="#7f1d1d" bg="#1c0a0a">{lbl}</Badge>;})}</>;
}
function PreviewBar({item}){
  const{id:rawId,displayName,registrationType,itemType,nutrition,saturation,fast,
    effects,blockType,sliceId,fluidFlowSlope,fluidFlowDecrease,createBottle,createBowl,
    bottleHasFood,bottleNutrition,bottleSaturation,bottleEffects,bottleTooltipKeys,
    bowlHasFood,bowlNutrition,bowlSaturation,bowlEffects,bowlTooltipKeys,
    tooltipKeys,hasVariants,variants,
    sliceHasFood,sliceNutrition,sliceSaturation,sliceEffects}=item;
  const{ns,localId,isCF}=parseNs(rawId);const id=localId;
  if(!id)return null;
  const isItem=registrationType==="item",isBlock=registrationType==="block",isFluid=registrationType==="fluid";
  const effSlice=sliceId||(id+"_slice");const isRaw=blockType==="block_raw_pie"||blockType==="block_raw_pizza";
  const itype=itemType||"food";const typeHasFood=itemTypeHasFood(itype);
  const tier=typeHasFood&&isItem?getNutritionTier(nutrition):null;
  const sliceTier=isBlock&&!isRaw&&sliceHasFood?getNutritionTier(sliceNutrition):null;
  const bottleTier=bottleHasFood&&isFluid&&createBottle?getNutritionTier(bottleNutrition):null;
  const bowlTier=bowlHasFood&&isFluid&&createBowl?getNutritionTier(bowlNutrition):null;
  const satWarns=[];
  if(typeHasFood&&isItem){const t=getNutritionTier(nutrition);if(saturation<t.satLo)satWarns.push("Sat below tier min");if(saturation>t.satHi)satWarns.push("Sat above tier max");}
  if(isItem&&fast&&nutrition>=7)satWarns.push("Tier 3+ .fast()");

  return(<div style={{display:"flex",flexWrap:"wrap",gap:5,alignItems:"center",padding:"6px 0"}}>
    <span style={{color:S.border,fontSize:16,lineHeight:1,flexShrink:0}}>|</span>
    {displayName&&<span style={{color:S.text,fontWeight:600,fontSize:12,marginRight:2}}>{displayName}</span>}
    <Badge color="#7dd3fc" border="#1e3a5f" bg="#0a1628"><code style={{fontFamily:"monospace",fontSize:11}}>{ns}:{id}</code></Badge>
    {hasVariants&&(variants||[]).length>0&&<Badge color="#34d399" border="#064e3b" bg="#022c22">{variants.length} variant{variants.length!==1?"s":""}</Badge>}
    <span style={{color:S.border,fontSize:14,lineHeight:1}}>|</span>

    <Badge color={isFluid?"#a78bfa":isBlock?"#f472b6":isItem?"#fb923c":"#60a5fa"} border={isFluid?"#4c1d95":isBlock?"#831843":isItem?"#7c2d12":"#1e3a5f"} bg={isFluid?"#1a0a2e":isBlock?"#1a0010":isItem?"#1c0a00":"#0a1628"}>{registrationType[0].toUpperCase()+registrationType.slice(1)}</Badge>

    {isItem&&<Badge color="#fb923c" border="#7c2d12" bg="#1c0a00">{itype[0].toUpperCase()+itype.slice(1)}{fast?" · Fast":""}</Badge>}
    {isItem&&<span style={{color:S.border,fontSize:14,lineHeight:1}}>|</span>}
    {isItem&&typeHasFood&&<><TierBadge tier={tier}/><EffectsBadge effects={effects}/><FoodBadges nutrition={nutrition} saturation={saturation}/></>}
    {isItem&&!typeHasFood&&<EffectsBadge effects={effects}/>}

    {isBlock&&<Badge color="#f472b6" border="#831843" bg="#1a0010">{blockType.replace("block_","").replace(/_/g," ").replace(/^\w/,c=>c.toUpperCase())}</Badge>}
    {isBlock&&<span style={{color:S.border,fontSize:14,lineHeight:1}}>|</span>}
    {isBlock&&!isRaw&&<Badge color="#7dd3fc" border="#1e3a5f" bg="#0a1628"><code style={{fontFamily:"monospace",fontSize:10}}>{ns}:{effSlice}</code></Badge>}
    {isBlock&&!isRaw&&sliceHasFood&&<><TierBadge tier={sliceTier}/><EffectsBadge effects={sliceEffects||[]}/><FoodBadges nutrition={sliceNutrition} saturation={sliceSaturation}/></>}

    {isFluid&&fluidFlowSlope>0&&<Badge color="#a78bfa" border="#4c1d95" bg="#1a0a2e">Flow {fluidFlowSlope},{fluidFlowDecrease}</Badge>}
    {isFluid&&<span style={{color:S.border,fontSize:14,lineHeight:1}}>|</span>}
    {isFluid&&createBottle&&<><Badge color="#7dd3fc" border="#1e3a5f" bg="#0a1628"><code style={{fontFamily:"monospace",fontSize:10}}>{ns}:{id}_bottle</code></Badge>{bottleHasFood&&<><TierBadge tier={bottleTier}/><EffectsBadge effects={bottleEffects||[]}/><FoodBadges nutrition={bottleNutrition} saturation={bottleSaturation}/></>}{!bottleHasFood&&<EffectsBadge effects={bottleEffects||[]}/>}{(bottleTooltipKeys||[]).map(k=><Badge key={k} color="#60a5fa" border="#1e3a5f" bg="#0a1628">{k}</Badge>)}</>}
    {isFluid&&createBottle&&createBowl&&<span style={{color:S.border,fontSize:14,lineHeight:1}}>|</span>}
    {isFluid&&createBowl&&<><Badge color="#7dd3fc" border="#1e3a5f" bg="#0a1628"><code style={{fontFamily:"monospace",fontSize:10}}>{ns}:{id}_bowl</code></Badge>{bowlHasFood&&<><TierBadge tier={bowlTier}/><EffectsBadge effects={bowlEffects||[]}/><FoodBadges nutrition={bowlNutrition} saturation={bowlSaturation}/></>}{!bowlHasFood&&<EffectsBadge effects={bowlEffects||[]}/>}{(bowlTooltipKeys||[]).map(k=><Badge key={k} color="#60a5fa" border="#1e3a5f" bg="#0a1628">{k}</Badge>)}</>}

    {(isItem||isBlock)&&(tooltipKeys||[]).map(k=><Badge key={k} color="#60a5fa" border="#1e3a5f" bg="#0a1628">{k}</Badge>)}

    {satWarns.map((w,i)=><Badge key={i} color={S.yellow} border="#713f12" bg="#1c1400">⚠ {w}</Badge>)}
  </div>);
}

const TABS=["Item","Recipes","Import / Export"];
export default function App(){
  const[panel,setPanel]=useState("Item");
  const[item,setItem]=useState(BLANK_ITEM);
  const[recipeState,setRecipeState]=useState(BLANK_RECIPES_STATE);
  const[sections,setSections]=useState({});
  const[savedItemFiles,setSavedItemFiles]=useState({});
  const[savedRecipeFiles2,setSavedRecipeFiles2]=useState({});
  const[savedItemSnapshots,setSavedItemSnapshots]=useState([]);
  const[savedRecs,setSavedRecs]=useState([]);



  const mergeFiles=(existing,next)=>{
    const out={...existing};
    const extractSetIds=s=>{const m=s&&s.match(/Set\.of\(([\s\S]*?)\)/);if(!m)return[];return m[1].split(",").map(x=>x.trim().replace(/^"|"$/g,"")).filter(Boolean);};
    const extractListIds=s=>{const m=s&&s.match(/List\.of\(([\s\S]*?)\)/);if(!m)return[];return m[1].split(",").map(x=>x.trim().replace(/^"|"$/g,"")).filter(Boolean);};
    for(const[k,v]of Object.entries(next)){
      if(k==="_autoSet"||k==="_variantSet")continue;
      if(typeof v==="string"&&typeof out[k]==="string"){
        if(k.endsWith("DisplayBlockRegistry.java")){
          const isExcluded=s=>s&&s.trimStart().startsWith("private static final Set<String> EXCLUDED_ITEMS");
          if(isExcluded(out[k])&&isExcluded(v)){const ids=[...new Set([...extractSetIds(out[k]),...extractSetIds(v)])];out[k]=buildDisplayBlockNote(ids);}
          else if(!out[k].includes(v))out[k]=out[k]+"\n\n"+v;
        } else if(k.endsWith("ModConfig.java")){
          const isDisable=s=>s&&s.trimStart().startsWith("public static final List<String> HIDE_ITEMS_DEFAULT");
          if(isDisable(out[k])&&isDisable(v)){const ids=[...new Set([...extractListIds(out[k]),...extractListIds(v)])];out[k]=buildModConfigNote(ids);}
          else if(!out[k].includes(v))out[k]=out[k]+"\n\n"+v;
        } else {
          if(!out[k].includes(v))out[k]=out[k]+"\n\n"+v;
        }
      } else if(k.includes("lang/en_us.json")&&v&&typeof v==="object"&&!Array.isArray(v)&&out[k]&&typeof out[k]==="object"&&!Array.isArray(out[k])){
        out[k]={...out[k],...v};
      } else {
        out[k]=v;
      }
    }
    return out;
  };
  const handleSave=()=>{
    const curItemFiles={...buildItemFiles({...item,sliceId:item.sliceId||(item.id?item.id+"_slice":"")})};
    delete curItemFiles._autoSet;delete curItemFiles._variantSet;
    const curAutoRec=buildAutoRecipeFiles({...item,sliceId:item.sliceId||(item.id?item.id+"_slice":"")});
    const curRecFiles=Object.fromEntries(recipeState.recs.map(r=>{const p=recipeFilePath(r);const j=buildRecipeJson(r);return p&&j?[p,j]:null}).filter(Boolean));
    setSavedItemFiles(prev=>mergeFiles(prev,curItemFiles));
    setSavedRecipeFiles2(prev=>mergeFiles(prev,{...curAutoRec,...curRecFiles}));
    setSavedItemSnapshots(prev=>[...prev,{...item}]);
    setSavedRecs(prev=>[...prev,...recipeState.recs]);
    setItem(BLANK_ITEM);
    setRecipeState(BLANK_RECIPES_STATE);
  };
  const handleReset=()=>{
    setSavedItemFiles({});
    setSavedRecipeFiles2({});
    setSavedItemSnapshots([]);
    setSavedRecs([]);
    setItem(BLANK_ITEM);
    setRecipeState(BLANK_RECIPES_STATE);
  };
  const handleBatchImport=(batchItemFiles,batchRecipeFiles,batchSnapshots)=>{
    setSavedItemFiles(prev=>mergeFiles(prev,batchItemFiles));
    setSavedRecipeFiles2(prev=>mergeFiles(prev,batchRecipeFiles));
    if(batchSnapshots&&batchSnapshots.length)setSavedItemSnapshots(prev=>[...prev,...batchSnapshots]);
  };
  const{localId:effId}=parseNs(item.id||"");
  const effSlice=item.sliceId||(effId?effId+"_slice":"");
  const curItemFiles=buildItemFiles({...item,sliceId:effSlice});
  const autoRecipeFiles=buildAutoRecipeFiles({...item,sliceId:effSlice});
  const recFiles=Object.fromEntries(recipeState.recs.map(r=>{const p=recipeFilePath(r);const j=buildRecipeJson(r);return p&&j?[p,j]:null}).filter(Boolean));
  const curRecipeFiles={...recFiles,...autoRecipeFiles};
  const itemFiles=mergeFiles(savedItemFiles,curItemFiles);
  const recipeFiles=mergeFiles(savedRecipeFiles2,curRecipeFiles);

  return (<div style={{fontFamily:"'DM Mono','Fira Code',monospace",background:S.bg,color:S.text,height:"100vh",display:"flex",flexDirection:"column",overflow:"hidden",fontSize:13}}>
    <div style={{borderBottom:"1px solid "+S.border,padding:"6px 16px",display:"flex",alignItems:"center",gap:8,flexShrink:0,flexWrap:"wrap"}}>
      <img src="data:image/png;base64,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" style={{width:22,height:22,borderRadius:4,flexShrink:0,imageRendering:"pixelated"}}/>
      <PreviewBar item={item}/>
    </div>
    <div style={{display:"flex",borderBottom:"1px solid "+S.border,flexShrink:0}}>
      {TABS.map(p=><button key={p} onClick={()=>setPanel(p)} style={{background:"none",border:"none",borderBottom:panel===p?"2px solid "+S.accent:"2px solid transparent",color:panel===p?S.text:S.muted,padding:"7px 14px",fontSize:12,cursor:"pointer",fontFamily:"inherit",fontWeight:panel===p?600:400,whiteSpace:"nowrap"}}>{p}</button>)}
    </div>
    <div style={{flex:1,minHeight:0,padding:"12px 16px",display:"flex",flexDirection:"column"}}>
      {panel==="Item"&&<ItemPanel item={item} onChange={setItem} sections={sections} setSections={setSections} onSave={handleSave} onReset={handleReset} hasSaved={Object.keys(savedItemFiles).length>0} allItemFiles={itemFiles}/>}
      {panel==="Recipes"&&<RecipesPanel recipeState={recipeState} setRecipeState={setRecipeState} item={item} sections={sections} setSections={setSections} autoRecipeFiles={autoRecipeFiles} onSave={handleSave} onReset={handleReset} hasSaved={Object.keys(savedItemFiles).length>0} allRecipeFiles={recipeFiles}/>}
      {panel==="Import / Export"&&<ImportExportPanel itemFiles={itemFiles} recipeFiles={recipeFiles} autoRecipeFiles={autoRecipeFiles} sections={sections} setSections={setSections} item={item} recipeState={recipeState} savedItemSnapshots={savedItemSnapshots} savedItemFiles={savedItemFiles} savedRecipeFiles2={savedRecipeFiles2} onBatchImport={handleBatchImport}/>}
    </div>
  </div>);
}