import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization

sealed class DrawerScreens(val title: String, val icon: ImageVector) {
    //Titulos de los fragmentos
    object Products : DrawerScreens("Productos (en construcción)", Icons.Default.ShoppingCart) //Icons: icono lateral de tabs con carrito
    object Company : DrawerScreens("Empresa", Icons.Default.Call) //Icono de telefono tab
}
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var currentScreen by remember { mutableStateOf<DrawerScreens>(DrawerScreens.Products) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(Modifier.height(24.dp))
                NavigationDrawerHeader()
                NavigationDrawerBody(
                    items = listOf(
                        DrawerScreens.Products,
                        DrawerScreens.Company
                    ),
                    onItemClick = { screen ->
                        currentScreen = screen
                        scope.launch { drawerState.close() }
                    },
                    selectedItem = currentScreen
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.title) },
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary
                    )
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                when (currentScreen) {
                    is DrawerScreens.Products -> ProductosScreen()
                    is DrawerScreens.Company -> EmpresaScreen()
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun NavigationDrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Migus",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color.Blue
            //color = Color(0xFF6A1B9A) //morado hexadecimal
            //Color primario de theme
            //color = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.height(8.dp))
        Divider()
    }
}

@Composable
fun NavigationDrawerBody(
    items: List<DrawerScreens>,
    onItemClick: (DrawerScreens) -> Unit,
    selectedItem: DrawerScreens
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        items.forEach { item ->
            val selected = item == selectedItem
            NavigationDrawerItem(
                label = {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .selectable(selected = selected) { onItemClick(item) },
                colors = NavigationDrawerItemDefaults.colors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedContainerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Composable
fun ProductosScreen() {
    Text(
        text = "Productos",
        style = MaterialTheme.typography.headlineMedium,
        color = Color.Blue
    )
}

// Modifica la EmpresaScreen para incluir los fragmentos Contáctenos y Quiénes Somos
@Composable
fun EmpresaScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Contáctenos", "Quiénes Somos")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (selectedTab) {
                0 -> ContactenosContent()
                1 -> QuienesSomosContent()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactenosContent() {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var ciudadExpandida by remember { mutableStateOf(false) }
    var ciudadSeleccionada by remember { mutableStateOf("") }
    var nacionalidadSeleccionada by remember { mutableStateOf("") }

    //Lista de principales Ciudades como Spinner, osea una lista desplegable para elegir
    val ciudadesGuayas = listOf(
        "Guayaquil",
        "Quito",
        "Cuenca",
        "Durán",
        "Samborondón",
        "Daule",
        "Salitre",
        "Yaguachi",
        "Milagro",
        "Nobol",
        "Lomas de Sargentillo"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Contáctenos",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp),
            fontWeight = FontWeight.Bold
        )

        // Campo Nombre
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Apellido
        OutlinedTextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text("Apellido") },
            leadingIcon = { Icon(Icons.Default.Person, null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Spinner Ciudad
        ExposedDropdownMenuBox(
            expanded = ciudadExpandida,
            onExpandedChange = { ciudadExpandida = !ciudadExpandida }
        ) {
            OutlinedTextField(
                value = ciudadSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Ciudad") },
                leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = ciudadExpandida) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = ciudadExpandida,
                onDismissRequest = { ciudadExpandida = false }
            ) {
                ciudadesGuayas.forEach { ciudad ->
                    DropdownMenuItem(
                        text = { Text(ciudad) },
                        onClick = {
                            ciudadSeleccionada = ciudad
                            ciudadExpandida = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Campo Dirección
        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            leadingIcon = { Icon(Icons.Default.Home, null) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // RadioButton de nacionalidad
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "Nacionalidad",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Opción Ecuatoriano
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = nacionalidadSeleccionada == "Ecuatoriano",
                        onClick = { nacionalidadSeleccionada = "Ecuatoriano" }
                    )
                ) {
                    RadioButton(
                        selected = nacionalidadSeleccionada == "Ecuatoriano",
                        onClick = { nacionalidadSeleccionada = "Ecuatoriano" }
                    )
                    Text(
                        text = "Ecuatoriano",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                // Opción Extranjero
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = nacionalidadSeleccionada == "Extranjero",
                        onClick = { nacionalidadSeleccionada = "Extranjero" }
                    )
                ) {
                    RadioButton(
                        selected = nacionalidadSeleccionada == "Extranjero",
                        onClick = { nacionalidadSeleccionada = "Extranjero" }
                    )
                    Text(
                        text = "Extranjero",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón Enviar
        Button(
            onClick = { /* Lógica de envío */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(Icons.Default.Send, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Enviar", style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun QuienesSomosContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nuestra Historia",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "En Migus, fusionamos la pasión por las hamburguesas jugosas y las pizzas artesanales desde 2012. " +
                    "Con ingredientes 100% frescos y recetas únicas, hemos revolucionado la comida rápida en Ecuador. " +
                    "Nuestro secreto: masa de pizza fermentada por 48h y carnes premium maduradas. " +
                    "¡Más de 1 millón de clientes satisfechos nos respaldan!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Nuestros Pilares",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp),
            fontWeight = FontWeight.Bold
        )

        val valores = listOf(
            "Hamburguesas: 200g de carne Angus con 3 tipos de maduración",
            "Pizzas: Horneadas en horno de piedra a 400°C",
            "Delivery express garantizado en menos de 30 min",
            "Opciones veganas y sin gluten",
            "Compromiso ecológico: Empaques biodegradables",
            "Open las 24hrs los 365 días del año"
        )

        valores.forEach { valor ->
            ListItem(
                headlineContent = { Text(valor) },
                leadingContent = {
                    Icon(
                        //Iconos de los valores de Quienes somos
                        //imageVector = Icons.Default.CheckCircle, // Icono de cheklist
                        imageVector = Icons.Default.Star, //Icono de estrella
                        contentDescription = null,
                        // Establece el color del icono al color primario
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
    }
}