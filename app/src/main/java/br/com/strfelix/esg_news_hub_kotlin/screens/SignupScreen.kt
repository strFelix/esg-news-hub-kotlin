package br.com.strfelix.esg_news_hub_kotlin.screens

import android.database.sqlite.SQLiteConstraintException
import android.util.Patterns
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import br.com.strfelix.esg_news_hub_kotlin.R
import br.com.strfelix.esg_news_hub_kotlin.model.User
import br.com.strfelix.esg_news_hub_kotlin.repository.user.UserRepository
import br.com.strfelix.esg_news_hub_kotlin.routes.Destination
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundPosition
import br.com.strfelix.esg_news_hub_kotlin.screens.components.BackgroundVector
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgBackgroundGradient
import br.com.strfelix.esg_news_hub_kotlin.ui.theme.EsgnewshubkotlinTheme

@Composable
fun SignupScreen(navController: NavController) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordShow by remember { mutableStateOf(false) }

    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    var showDialogError by remember { mutableStateOf(false) }
    var showDialogSuccess by remember { mutableStateOf(false) }

    fun validate(): Boolean {
        isNameError = name.length < 3
        isEmailError = email.length < 3 || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
        isPasswordError = password.length < 3
        return !isNameError && !isEmailError && !isPasswordError
    }

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
                text = stringResource(id= R.string.signup_title),
                fontSize = 24.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                placeholder = { Text(text=stringResource(id = R.string.name_hint)) },
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Next
                ),
                isError = isNameError,
                trailingIcon = {
                    if (isNameError){
                        Icon(imageVector = Icons.Default.Error, contentDescription = null)
                    }
                },
                supportingText = {
                    if (isNameError){
                        Text(
                            text = stringResource(R.string.username_is_required),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text=stringResource(id = R.string.email_hint))},
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isError = isEmailError,
                trailingIcon = {
                    if (isEmailError){
                        Icon(imageVector = Icons.Default.Error, contentDescription = null)
                    }
                },
                supportingText = {
                    if (isEmailError){
                        Text(
                            text = stringResource(R.string.email_is_required),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text=stringResource(id = R.string.password_hint))},
                visualTransformation =
                    if (passwordShow) VisualTransformation.None
                    else PasswordVisualTransformation(),
                isError = isPasswordError,
                trailingIcon = {
                    if(isPasswordError){
                        Icon(imageVector = Icons.Default.Error, contentDescription = "")
                    } else {
                        IconButton(onClick = {
                            passwordShow = !passwordShow
                        }) {
                            Icon(
                                imageVector =
                                    if (passwordShow)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                contentDescription = stringResource(R.string.show_password_description)
                            )
                        }
                    }
                },
                supportingText = {
                    if (isPasswordError){
                        Text(
                            text = stringResource(R.string.password_is_required),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row (horizontalArrangement = Arrangement.spacedBy(16.dp)
            )
            {

                Button(
                    onClick = {
                        if(validate()){
                            val user = User(
                                name = name,
                                email = email,
                                password = password
                            )
                            try {
                                userRepository.saveUser(user)
                                showDialogSuccess = true
                            } catch (e: SQLiteConstraintException){
                                isEmailError = true
                                showDialogError = true
                            }
                        }
                        else {
                            showDialogError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.signup_button),
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }

                OutlinedButton(
                    onClick = { navController.navigate("initial")},
                    border = BorderStroke(2.dp, Color.Black),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.cancel_button),
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                if (showDialogSuccess){
                    AlertDialog(
                        onDismissRequest = { showDialogError = false },
                        title = {
                            Text(text = stringResource(R.string.success))
                        },
                        text = {
                            Text(text = stringResource(R.string.account_create_dialog))
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialogSuccess = false
                                navController.navigate(Destination.LoginScreen.route) }
                            ) {
                                Text(text = "OK")
                            }
                        }

                    )
                }
                if (showDialogError){
                    AlertDialog(
                        onDismissRequest = { showDialogError = false },
                        title = {
                            Text(text = stringResource(R.string.try_again))
                        },
                        text = {
                            Text(text = stringResource(R.string.try_again_dialog))
                        },
                        confirmButton = {
                            TextButton(onClick = { showDialogError = false }) {
                                Text(text = "OK")
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    EsgnewshubkotlinTheme {
        SignupScreen(navController = rememberNavController())
    }
}