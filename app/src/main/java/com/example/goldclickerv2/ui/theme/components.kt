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
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    GoldClicker(goldCount, perClick)
                    TextDescription(goldCount.intValue, perClick.intValue)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Color.LightGray)
                    .padding(10.dp)
                    .fillMaxWidth()
                    .weight(1f) // Makes the shop section fill available space
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "SHOP", fontSize = 24.sp)
                    Buttons(goldCount, perClick)
                }
            }
        }
    }
}

@Composable
fun GoldImage() {
    Image(
        painter = painterResource(id = R.drawable.goldcoin),
        contentDescription = null,
        modifier = Modifier.size(200.dp)
    )
}

@Composable
fun GoldClicker(goldCount: MutableIntState, perClick: MutableIntState) {
    Button(
        onClick = { goldCount.intValue += perClick.intValue },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(0.dp)
    ) {
        GoldImage()
    }
}

@Composable
fun TextDescription(gold: Int, perClick: Int) {
    Text(
        text = "You have $gold coins.",
        fontSize = 20.sp
    )
    Text(
        text = "Per click: $perClick"
    )
    Text(
        text = "Click the coin to earn more!",
        fontSize = 12.sp
    )
}

@Composable
fun Buttons(goldCount: MutableIntState, perClick: MutableIntState) {
    LazyColumn(
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxHeight()
    ) {
        item {
            ShopRow(15, "Miner", R.drawable.miner_icon, goldCount, perClick, increase = 1)
            ShopRow(75, "Excavator", R.drawable.excavator_icon, goldCount, perClick, increase = 2)
            ShopRow(625, "Shovel", R.drawable.shovel_icon, goldCount, perClick, increase = 5)
            ShopRow(2000, "Cart", R.drawable.cart_icon, goldCount, perClick, increase = 10)
            ShopRow(12500, "Crane", R.drawable.crane_icon, goldCount, perClick, increase = 20)
            ShopRow(80000, "Factory", R.drawable.factory_icon, goldCount, perClick, increase = 50)
            ShopRow(300000, "Gold Mine", R.drawable.goldmine_icon, goldCount, perClick, increase = 100) }
    }
}

@Composable
fun ShopRow(
    price: Int,
    name: String,
    imageResId: Int,
    goldCount: MutableIntState,
    perClick: MutableIntState,
    amountOwned: Int = 0,
    increase: Int
) {
    var owned by remember { mutableIntStateOf(amountOwned) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.DarkGray),
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
            Text(text = "$name (Per click: +$increase)", fontSize = 14.sp, color = Color.White)
            Text(text = "You own: $owned", fontSize = 10.sp, color = Color.White)
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
                containerColor = Color.Gray,
                contentColor = Color.White
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
