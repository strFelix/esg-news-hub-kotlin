package br.com.strfelix.esg_news_hub_kotlin.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.strfelix.esg_news_hub_kotlin.R
import br.com.strfelix.esg_news_hub_kotlin.repository.user.UserRepository
import br.com.strfelix.esg_news_hub_kotlin.routes.Destination
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundPosition
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundVector
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgBackgroundGradient
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgnewshubkotlinTheme
import br.com.strfelix.esg_news_hub_kotlin.viewModel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, authViewModel: AuthViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordShow by remember { mutableStateOf(false) }
    var authenticateError by remember { mutableStateOf(false) }

    val userRepository: UserRepository =
        UserRepository(LocalContext.current)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                EsgBackgroundGradient
            ),
        contentAlignment = Alignment.Center
    ) {
        BackgroundVector(position = BackgroundPosition.Top)
        BackgroundVector(position = BackgroundPosition.Bottom)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon (
                    painter = painterResource(id = R.drawable.logo_esg),
                    contentDescription = "Logo ESG News",
                    tint = Color.Black,
                )
                Spacer(modifier = Modifier.height(24.dp))


            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(id=R.string.login_title),
                fontSize = 24.sp,
                color = Color.Black

            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text=stringResource(id = R.string.email_hint)) },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text=stringResource(id = R.string.password_hint)) },

                visualTransformation =
                    if (passwordShow)
                        VisualTransformation.None
                    else
                        PasswordVisualTransformation(),

                trailingIcon = {
                    IconButton(onClick = {
                        passwordShow = !passwordShow
                    }) {
                        Icon(
                            imageVector =
                                if (passwordShow)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                            contentDescription = R.string.show_password_description.toString()
                        )
                    }
                },

                shape = RoundedCornerShape(10.dp),

                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row (
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            )
            {

                Button(
                    onClick = {
                        val authenticate =
                            userRepository.login(email, password)
                        if (authenticate) {
                            authViewModel.loggedUser = userRepository.getUserByEmail(email)
                            navController.navigate(
                                Destination.HomeScreen.route
                            )
                        } else {
                            authenticateError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(id=R.string.login_button),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                if (authenticateError){
                    Row {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = stringResource(R.string.authentication_error),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = { },
                    border = BorderStroke(2.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(id=R.string.cancel_button),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LoginPreview() {
//    EsgnewshubkotlinTheme {
//        LoginScreen(
//            navController = rememberNavController(),
//            authViewModel = AuthViewModel()
//        )
//    }
//}