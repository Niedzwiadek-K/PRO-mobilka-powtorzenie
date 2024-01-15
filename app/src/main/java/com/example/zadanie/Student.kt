package com.example.zadanie

import com.fasterxml.jackson.annotation.JsonProperty

data class Student(
    @JsonProperty("name") val name: String,
    @JsonProperty("age") val age: Int,
    @JsonProperty("gpa") val gpa: Double
)
