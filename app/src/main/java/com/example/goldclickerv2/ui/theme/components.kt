import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.goldclickerv2.R


@Composable
fun MainBox(goldCount: MutableIntState, perClick: MutableIntState) {

    val modeMultiplier = remember { mutableIntStateOf(1) } // 1 = light mode, -1 = dark mode
    val backgroundColor = if (modeMultiplier.intValue == 1) Color.White else Color.Black
    val textColor = if (modeMultiplier.intValue == 1) Color.Black else Color.White
    val secondaryBackgroundColor = if (modeMultiplier.intValue == 1) Color.LightGray else Color.DarkGray

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = secondaryBackgroundColor)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    GoldClicker(goldCount, perClick)
                    TextDescription(goldCount.intValue, perClick.intValue, textColor, backgroundColor, modeMultiplier)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = secondaryBackgroundColor)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "SHOP", fontSize = 24.sp, color = textColor)
                    Buttons(goldCount, perClick, modeMultiplier.intValue, backgroundColor )
                }
            }
        }
    }
}

@Composable
fun GoldClicker(goldCount: MutableIntState, perClick: MutableIntState) {
    Button(
        onClick = { goldCount.intValue += perClick.intValue },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.goldcoin),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun TextDescription(gold: Int, perClick: Int, textColor: Color, backgroundColor: Color, modeMultiplier: MutableIntState) {
    Text(
        text = "You have $gold coins.",
        fontSize = 20.sp,
        color = textColor
    )
    Text(
        text = "Per click: $perClick",
        color = textColor
    )
    Text(
        text = "Click the coin to earn more!",
        fontSize = 12.sp,
        color = textColor
    )
    Button(
        onClick = { modeMultiplier.intValue *= -1 },
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
    ) {
        Text(text = if (modeMultiplier.intValue == 1) "Switch to Dark Mode" else "Switch to Light Mode")
    }
}

@Composable
fun Buttons(goldCount: MutableIntState, perClick: MutableIntState, modeMultiplier: Int, backgroundColor: Color) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            ShopRow(15, "Miner", R.drawable.miner_icon, goldCount, perClick, increase = 1, modeMultiplier, backgroundColor)
            ShopRow(75, "Excavator", R.drawable.excavator_icon, goldCount, perClick, increase = 2, modeMultiplier, backgroundColor)
            ShopRow(625, "Shovel", R.drawable.shovel_icon, goldCount, perClick, increase = 5, modeMultiplier, backgroundColor)
            ShopRow(2000, "Cart", R.drawable.cart_icon, goldCount, perClick, increase = 10, modeMultiplier, backgroundColor)
            ShopRow(12500, "Crane", R.drawable.crane_icon, goldCount, perClick, increase = 20, modeMultiplier, backgroundColor)
            ShopRow(80000, "Factory", R.drawable.factory_icon, goldCount, perClick, increase = 50, modeMultiplier, backgroundColor)
            ShopRow(300000, "Gold Mine", R.drawable.goldmine_icon, goldCount, perClick, increase = 100, modeMultiplier, backgroundColor)
        }
    }
}

@Composable
fun ShopRow(
    price: Int,
    name: String,
    imageResId: Int,
    goldCount: MutableIntState,
    perClick: MutableIntState,
    increase: Int,
    modeMultiplier: Int,
    backgroundColor: Color
) {
    var owned by remember { mutableIntStateOf(0) }

    val textColor = if (modeMultiplier == 1) Color.Black else Color.White

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(15.dp))
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "$name icon",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 8.dp)
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(1f)
                .padding(2.dp)
        ) {
            Text(text = "$name (Per click: +$increase)", fontSize = 14.sp, color = textColor)
            Text(text = "You own: $owned", fontSize = 10.sp, color = textColor)
        }

        Button(
            onClick = {
                if (goldCount.intValue >= price) {
                    goldCount.intValue -= price
                    perClick.intValue += increase
                    owned++
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "BUY ($price COINS)")
        }
    }
}


@Preview
@Composable
fun MainBoxAssemble() {
    val goldCount = remember { mutableIntStateOf(0) }
    val perClick = remember { mutableIntStateOf(1) }
    MainBox(goldCount, perClick)
}
